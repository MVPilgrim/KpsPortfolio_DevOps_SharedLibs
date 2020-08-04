//
// Shared library step: log in to IBM Bluemix.
//
//

def call(bmxOrg,bmxSpace,bmxApi,bmxApiKey) {
  echo "LoginToBluemix entered.";
  
  bmxApi == "" ? bmxApi = 'https://api.ng.bluemix.net' : null;
  
  if (bmxApiKey == "") {
    // Key is the same for all environments.
    if (bmxSpace == "prod1" || bmxSpace == "prod2" || bmxSpace == "stage1" || bmxSpace == "stage2") {
      bmxApiKey = "fV-vOPO70A4o2r7oKGScqsYUpJJqLY-_FDi6_GpRWBz_";
    } else {
      bmxApiKey = "fV-vOPO70A4o2r7oKGScqsYUpJJqLY-_FDi6_GpRWBz_";
    }
  }
  
  httpProxyURLToCloud  = 'http_proxy=http://inetgw.aa.com:9093';
  httpsProxyURLToCloud = 'https_proxy=http://inetgw.aa.com:9093';
  
  withEnv(["${httpProxyURLToCloud}", "${httpsProxyURLToCloud}",'CF_STARTUP_TIMEOUT=500', "CF_HOME=${WORKSPACE}"]) {              
    sh """
      cf api $bmxApi
      cf login -u apikey -p $bmxApiKey -o $bmxOrg -s $bmxSpace
    """
  }
  echo "LoginToBluemix exited.";
}      
