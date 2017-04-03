<?php

function check_segments($segments, $limits) {
  if(!is_array($segments)) {
    return false;
  }
  
  foreach($segments as $seg) {
    if($seg[0] < $limits[0] || $seg[2] < $limits[0]
    || $seg[0] > $limits[1] || $seg[2] > $limits[1]
    || $seg[1] < $limits[2] || $seg[3] < $limits[2]
    || $seg[1] > $limits[3] || $seg[3] > $limits[3]
    ) {
     return false; 
    }
    
    if($seg[0] != $seg[2] && $seg[1] != $seg[3]) {
      return false;
    }
  }
  
  return true;
}

function check_file($filename) {
  $datas = file($filename, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
  $limits = array_shift($datas);
  $limits = array_map('intval', explode(' ', $limits));
  if(count($limits) != 4) {
    print 'Limits count is '.count($limits)."\n";
  }
  /*
  print 'Limits :'."\n";
  print_r($limits);
  //*/
  $segments = [];
  foreach($datas as $i => $data) {
    $s = array_map('intval', explode(' ', $data));
    if(count($s) != 4) {
      print 'Segment count is '.count($s)."\n";
    }
    $segments[] = $s;
  }
  
  $good = check_segments($segments, $limits);
  $count = count($segments);
  print "File $filename : Segments ($count) are ".($good ? 'all good' : 'not all good')."\n";
  
  return $segments;
}

$dir = '../scenes';
$files = @scandir($dir);
foreach($files as $file) {
  // pass hidden file and links
  if($file[0] == '.') {
    continue;
  }
  check_file($dir.'/'.$file);
}
