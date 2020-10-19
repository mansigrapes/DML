package com.dealermela.util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;

import java.io.File;

public class DownloadImages {
    Context myContext;
    String myDownlaodURL;
    String mySdCardSaveImagePath;

    public DownloadImages(Context theContext, String theUrl, String thePath) {
        myContext = theContext;
        myDownlaodURL = theUrl;
        mySdCardSaveImagePath = thePath;

        String PhotoPictureName = getFilename(myDownlaodURL);
        File PhotoPictureSavePath = new File(mySdCardSaveImagePath + "/" + PhotoPictureName);
        if (PhotoPictureSavePath.exists()) {
            return;
        }
        if (!PhotoPictureSavePath.exists()) {
            download();
        }
    }

    public String getFilename(String theFileName) {

        String filename = theFileName.substring(theFileName.lastIndexOf("/") + 1, theFileName.length());
        return filename;
    }

    public void download() {
        Uri Download_Uri = Uri.parse(myDownlaodURL);
        DownloadManager downloadManager = (DownloadManager) myContext.getSystemService(myContext.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(true);
        //Set the local destination for the downloaded file to a path within the application's external files directory
        //request.setDestinationInExternalFilesDir(myContext, mySdCardSaveImagePath, split[split.length - 1]);
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, split[split.length-1]);

        String[] split = myDownlaodURL.split("/");
        //Set the local destination for the downloaded file to the folder specified by user.
        File destinationFile = new File(mySdCardSaveImagePath, split[split.length - 1]);

//        request.setDestinationUri(Uri.fromFile(destinationFile));
//        Uri uri = Uri.fromFile(destinationFile);
//
        if (Build.VERSION.SDK_INT >= 29){
            File newcheck = new File(mySdCardSaveImagePath);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(myDownlaodURL);
//            request.setDestinationInExternalFilesDir(this,Uri.fromFile(destinationFile)]);
//            request.setDestinationUri(uri);
            request.setDestinationInExternalFilesDir(myContext,newcheck.getAbsolutePath(),split[split.length - 1]);
        }else {
            request.setDestinationUri(Uri.fromFile(destinationFile));
        }

        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle(split[split.length - 1]);
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription(mySdCardSaveImagePath);

        request.setVisibleInDownloadsUi(true);

        //Enqueue a new download and get the reference Id
        long downloadReference = downloadManager.enqueue(request);
    }
}

//change the extension of image after downloading


