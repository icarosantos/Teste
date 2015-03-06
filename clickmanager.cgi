#!/usr/bin/perl -Tw
###############################################################################
# Click Manager 2.2.5
# Copyright (C) 2003  Aardvark Industries
# http://www.aardvarkind.com/index.php?page=clickmanager
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
###############################################################################

# Load modules and whatnot.
use strict;
use CGI::Carp qw(fatalsToBrowser);
use Fcntl qw(:DEFAULT :flock);

# Declare variables that will be used later in the script.
my $datenum;

# Settings that you might need to change.
my $dir = '.';   # The full path to the directory this file is in.

# Check to see if it's a new day.
my($sec, $min, $hour, $mday, $mon, $year, $wday, $yday, $isdst) = localtime(time);
sysopen(DATE, "$dir/date.txt", O_RDWR) || die "$dir/date.txt\n\n$!, stopped";
flock(DATE, 2);
my $date = <DATE>;
unless ($date == $mday) {
  $datenum = 1;
  truncate(DATE, 0);
  seek(DATE, 0, 0);
  print DATE $mday;
}
close(DATE);

# If the preceding code deemed that it is a new day, update total.txt accordingly.
if ($datenum) {
  sysopen(TOTAL, "$dir/total.txt", O_RDWR) || die "$dir/total.txt\n\n$!, stopped";
  flock(TOTAL, 2);
  my($hits_today, $hits_1, $hits_2, $hits_3, $hits_highest, $hits_total, $hits_days) = split(/\|/, <TOTAL>);
  if ($hits_today > $hits_highest) { $hits_highest = $hits_today; }
  $hits_days++;
  truncate(TOTAL, 0);
  seek(TOTAL, 0, 0);
  print TOTAL join('|', "0", $hits_today, $hits_1, $hits_2, $hits_highest, $hits_total, $hits_days);
  close(TOTAL);
}

# Open the clicks data file
sysopen(CLICKMANAGER, "$dir/clickmanager.txt", O_RDONLY) || die "$dir/clickmanager.txt\n\n$!, stopped";
flock(CLICKMANAGER, 2);
my @db = <CLICKMANAGER>;
close(CLICKMANAGER);

# Process the query string
my @query = split(/=/, $ENV{'QUERY_STRING'});
my $action = shift(@query);
my $value = join('=', @query);

# Checks to see what part of the script is wanted.
if ($action eq "dl") { &dl; }            # Redirect to a download.
elsif ($action eq "num") { &num; }       # Display number of clicks.
elsif ($action eq "numall") { &numall; } # Display total number of clicks.
else { &stats; }                         # Link to the stats and graphs page.



# Redirect to a download.
sub dl {
  # Used to determine if the requested download is new.
  my $done2 = 0;

  # Replaces IDs with URLs.
  &checkid($value);

  # Updates the clicks file
  sysopen(CLICKMANAGER, "$dir/clickmanager.txt", O_WRONLY|O_TRUNC) || die "$dir/clickmanager.txt\n\n$!, stopped";
  flock(CLICKMANAGER, 2);
  foreach (@db) {
    chomp;
    my ($id, $url2, $hits) = split(/\|/);
    if ($url2 eq $value) {
      $hits++;
      print CLICKMANAGER join('|', $id, $url2, $hits) . "\n";
      $done2 = 1;
    }
    else { print CLICKMANAGER "$_\n"; }
  }
  close(CLICKMANAGER);

  # If the site is a new one, make a new entry in the data file.
  unless ($done2) {
    my @db = sort { $b <=> $a }@db;
    my($num, $url2, $hits) = split(/\|/, $db[0]);
    $num++;
    sysopen(CLICKMANAGER, "$dir/clickmanager.txt", O_WRONLY|O_APPEND) || die "$dir/clickmanager.txt\n\n$!, stopped";
    flock(CLICKMANAGER, 2);
    print CLICKMANAGER join('|', $num, $value, 1) . "\n";
    close(CLICKMANAGER);
  }

  # Update the total clicks file.
  sysopen(TOTAL, "$dir/total.txt", O_RDWR) || die "$dir/total.txt\n\n$!, stopped";
  flock(TOTAL, 2);
  my($hits_today, $hits_1, $hits_2, $hits_3, $hits_highest, $hits_total, $hits_days) = split(/\|/, <TOTAL>);
  $hits_today++;
  $hits_total++;
  truncate(TOTAL, 0);
  seek(TOTAL, 0, 0);
  print TOTAL join('|', $hits_today, $hits_1, $hits_2, $hits_3, $hits_highest, $hits_total, $hits_days);
  close(TOTAL);

  # Send the browser to the appropriate URL.
  print "Location: $value\n\n";
}



# Display number of clicks.
sub num {
  # Get a list of all the sites whose clicks need to be totaled.
  my @urls = split(/\|\|\|/, $value);

  # Start the count at 0.
  my $num = 0;

  # Go through all the sites and add up their clicks.
  foreach my $line(@urls) {
    &checkid($line);
    foreach (@db) {
      chomp;
      my ($id, $url, $hits) = split(/\|/);
      if ($line eq $url) { $num+=$hits; last; }
    }
  }

  # Print the Content-type and the number of hits.
  print "Content-type: text/html\n\n$num";
}



# Display total number of clicks.
sub numall {
  # Start the counter at 0.
  my $num = 0;

  # Go through the data file and add up all the hits.
  foreach (@db) {
    chomp;
    my($id, $url, $hits) = split(/\|/);
    $num+=$hits;
  }

  # Print the Content-type and the number of hits.
  print "Content-type: text/html\n\n$num";
}



# Link to the stats and graphs page.
sub stats {
  print "Content-type: text/html\n\n<a href=\"stats.cgi\">Click Here</a>";
}



# Replaces IDs with URLs.
sub checkid {
  # Gets set to 1 if there is an invalid ID.
  my $done = 0;

  # Loops through what is passed to this subroutine and checks it.
  foreach my $line(@_) {
    unless ($line =~ "http:") {
      foreach (@db) {
        chomp;
        my($id, $url, $hits) = split(/\|/);
        if ($id == $line) {
          $line = $url;
          $done = 1;
          last;
        }
      }
      unless ($done) { &error("Invalid ID or URL"); }
    }
  }
}



# Used for some error messages.
sub error {
  print "Content-type: text/html\n\n$_[0]\n";
  exit;
}