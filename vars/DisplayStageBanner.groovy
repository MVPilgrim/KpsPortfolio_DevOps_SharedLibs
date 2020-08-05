/**********************************************************************
/* Module: CreateDevTestRelease.groovy
/*
/* Description: Display an "eye-catcher" banner in the
/*              log file for the pipeline stage being executed.
/*
/* Change Log:
/*   * KPS 08-04-2020: Initial version.
/********************************************************************/


def call(stageName) {
  topBottomString = "***************************************************************************************";
  middleString    = "*                                                                                     *";
  blanks          = "                                                                                       ";
  
  stageNameLen = stageName.length();
  padLen       = topBottomString.length();
  padLen       = ((padLen - stageNameLen - 1) / 2) as Integer;
  subBlanks    = blanks.substring(0,padLen);
  
  stageLine = "*" + subBlanks + stageName + subBlanks + "*";
  
  echo """\
$topBottomString
$middleString
$middleString
$stageLine
$middleString
$middleString
$topBottomString \
"""
}