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
  halfLen  = ((stageNameLen / 2) - 1) as Integer;
  subBlanks = blanks.substring(0,halfLen);
  
  echo topBottomString;
  echo middleString;
  echo middleString;
  echo "*" + subBlanks + stageName + subBlanks + "*";
  echo middleString;
  echo middleString;
  echo topBottomString;

}