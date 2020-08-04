//
// Shared library step: deploy app to Bluemix.
//
//

def call(appName, manifestFile, options, targetFile, bmxSpace) {
  echo "DeployToBluemix entered.";
  
  manifestFile = (manifestFile != "") ? "-f $manifestFile" : "--no-manifest"; 
  targetFile   = (targetFile   != "") ? "-p $targetFile"   : targetFile;
  
  varArg = "";
  //if (bmxSpace != null && bmxSpace != "") {
  //  varArg = "--var ENV=$bmxSpace";
  //}
  
  echo "DeployToBluemix: appName,manifestFile,options,targetFile: $appName,$manifestFile,$options,$targetFile";
  
  httpProxyURLToCloud  = 'http_proxy=http://inetgw.aa.com:9093';
  httpsProxyURLToCloud = 'https_proxy=http://inetgw.aa.com:9093';
  
  withEnv(["${httpProxyURLToCloud}", "${httpsProxyURLToCloud}",'CF_STARTUP_TIMEOUT=500', "CF_HOME=${WORKSPACE}"]) {
    sh """
      cf push ${appName} $manifestFile ${options} ${varArg} $targetFile
    """
  }
  
  echo "DeployToBluemix exited.";
}      
