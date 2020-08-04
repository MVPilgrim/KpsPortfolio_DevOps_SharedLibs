//
// Shared library step: run all steps to deploy to the Bluemix cloud.
//
//

def call(gitRepoDir,appName,version,appNameSuffix,manifestFile,targetFileDir,packageType,bmxOrg,bmxSpace,bmxRegion) {
  echo "DeployToBluemixAllSteps entered.";
  
  dir("${gitRepoDir}") {
    try {
      isDevOrTest = false;
	    if (bmxSpace == "dev" || bmxSpace ==  "test") {
        isDevOrTest = true;
      }
      
      if (bmxRegion == "east") {
        bmxApi = 'https://api.us-east.bluemix.net';
      } else {
        bmxApi = 'https://api.ng.bluemix.net';
      }
      
      appNameWithSuffix = appName + appNameSuffix;
      bmxAppName = appNameWithSuffix.replace("_","-");
      bmxAppName = bmxAppName + "-" + bmxSpace + "-" + bmxRegion;
      
      bmxOptions = "";
      if (!isDevOrTest) {
        bmxOptions = "--no-route ";
      } else {      
        echo "Displaying $manifestFile";
        sh "cat $manifestFile";
        try {
          manifestFields = readYaml file: "${manifestFile}";
          manApplicationsFields = manifestFields.applications[0];
          hasRoute = (manApplicationsFields.no-route == true) ? false : true;
        } catch (Exception e) { 
          hasRoute = true;
        }
        bmxOptions = "";
        if (hasRoute) {
          //bmxAppName += "-" + bmxSpace + "-south";
          bmxOptions = "$bmxOptions --hostname $bmxAppName";
        } else {
          bmxOptions = "$bmxOptions --no-route";
        }
      }
      
      //if (doC2cApp) {
      //  bmxOptions += " --no-start";
      //}
      
      echo "DeployToBluemixAllSteps: bmxOptions: $bmxOptions";
      httpProxyURLToCloud  = 'http_proxy=http://inetgw.aa.com:9093';
      httpsProxyURLToCloud = 'https_proxy=http://inetgw.aa.com:9093';
      
      withEnv(["${httpProxyURLToCloud}", "${httpsProxyURLToCloud}",'CF_STARTUP_TIMEOUT=500', "CF_HOME=${WORKSPACE}"]) {
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'SP-MCT-GIT-Credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
  
          LoginToBluemix(bmxOrg,bmxSpace,bmxApi,"");
          
          if (targetFileDir != null && targetFileDir != "") {
            deployFile = "${targetFileDir}/${appName}-${version}.${packageType}";
          } else {
            deployFile =  "";
          }
          DeployToBluemix(bmxAppName, manifestFile, bmxOptions, deployFile,bmxSpace);
          
          if (!isDevOrTest) {
            MapAkamaiRoutesToApp(bmxAppName,bmxSpace);
          }
          
        }
      }
    }
    catch (Exception e) {
      echo "DeployToBluemixAllSteps: exception caught issuing remote commands.: " + e.getMessage();
      throw e;
    }
  }
  echo "DeployToBluemixAllSteps exited.";
}      
