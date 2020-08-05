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
  
  int stageNameLen = stageName.length();
  //stageNameLen  = 40;
  halfLen  = int ((stageNameLen / 2) - 1);
  subBlanks = blanks.substring(0,halfLen);
  
  echo topBottomString;
  echo middleString;
  echo middleString;
  echo "*" + blanks.subBlanks + stageName + subBlanks + "*";
  echo middleString;
  echo middleString;
  echo topBottomString;

}