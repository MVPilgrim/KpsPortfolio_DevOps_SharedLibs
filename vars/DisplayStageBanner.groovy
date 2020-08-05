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
  blanksLen    = blanks.length();
  padLen       = ((blanksLen - stageNameLen - 1) / 2) as Integer;
  padBlanks    = blanks.substring(0,padLen);
  
  echo """
  $topBottomString
  $middleString
  $middleString
  *$(padBlanks)$(stageName)$(padBlanks)*
  $middleString
  $middleString
  $topBottomString
  """
}