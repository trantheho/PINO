package com.example.loginpino.tutorial.pdf

import android.arch.lifecycle.ViewModel
import android.os.Environment
import android.system.Os.read
import com.example.loginpino.AppUtil
import com.example.loginpino.tutorial.CheckForSDCard
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class PdfActivityViewModel: ViewModel() {

    internal var apkStorage: File? = null
    internal var outputFile: File? = null


    fun downloadFile(arg0: String): File?
    {
        try
        {
            val url = URL(arg0[0])//Create Download URl
            val c = url.openConnection() as HttpURLConnection//Open Url Connection
            c.requestMethod = "GET"//Set Request Method to "GET" since we are grtting data
            c.connect()//connect the URL Connection
             //Get File if SD card is present
            if (CheckForSDCard().isSDCardPresent())
            {
                apkStorage = File((Environment.getExternalStorageDirectory()).toString() + "/" + "ncsw")
            }
            else
                AppUtil.toast("Oops!! There is no SD Card.")

             //If File is not present create directory
            if (!apkStorage!!.exists())
            {
                apkStorage!!.mkdir()
            }

            outputFile = File(apkStorage, getLinkName(arg0[0]))//Create Output file in Main File

             //Create New File if not present
            if (!outputFile!!.exists())
            {
                outputFile!!.createNewFile()
            }

            val fos = FileOutputStream(outputFile)//Get OutputStream for NewFile Location

            val ins = c.inputStream//Get InputStream for connection

            val buffer = ByteArray(1024)//Set buffer type
            var len1 = 0//init length
            while ((len1 = ins.read(buffer)) != -1)
            {
                fos.write(buffer, 0, len1)//Write new file
            }

             //Close all connection after doing task
            fos.close()
            ins.close()

            }
            catch (e:Exception) {
             //Read exception if something went wrong
                e.printStackTrace()
                outputFile = null
            }

        return outputFile
    }
}