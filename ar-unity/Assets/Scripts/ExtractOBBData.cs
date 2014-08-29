using UnityEngine;
using System.Collections;
using System.IO;
using System.Text;

public class ExtractOBBData : MonoBehaviour 
{

    string streamingXmlPath = Application.streamingAssetsPath + "/QCAR/museoepn.xml";
    string streamingDatPath = Application.streamingAssetsPath + "/QCAR/museoepn.dat";

    string persistentXmlPath = Application.persistentDataPath + "/museoepn.xml";
    string persistentDatPath = Application.persistentDataPath + "/museoepn.dat";

    bool isLoadAllData = false;
    bool isLoadXml = false;
    bool isLoadDat = false;

    WWW wwwXml;
    WWW wwwDat;

    // Use this for initialization

    void Start()
    {
        //load xml file
        if (!File.Exists(persistentXmlPath))
        {
            
            wwwXml = new WWW(streamingXmlPath);
        }
        else
        {
            isLoadXml = true;
        }

        if (!File.Exists(persistentDatPath))
        {
            
            wwwDat = new WWW(streamingDatPath);
        }
        else
        {
            isLoadDat = true;
        }
    }


    void Update()
    {

        if (isLoadAllData == false)
        {
            if (isLoadXml == false)
            {
                if (wwwXml.isDone)
                {
                    File.WriteAllBytes(persistentXmlPath, wwwXml.bytes);
                    Debug.Log("Unity load scene, xml writed: " + streamingXmlPath);
                    isLoadXml = true;
                }
                else
                {
                    Debug.Log("LoadingTrackerXML...(Only first time)");
                }

            }

            if (isLoadDat == false)
            {
                if (wwwDat.isDone)
                {
                    File.WriteAllBytes(persistentDatPath, wwwDat.bytes);
                    Debug.Log("Unity load scene, data writed: " + streamingDatPath);
                    isLoadDat = true;
                }
                else
                {
                    Debug.Log("0 LoadingTrackerData...(Only first time)");
                }
            }

            if (isLoadXml && isLoadDat)
            {
                //isLoadAllData = true;
                Application.LoadLevel(1);
            }

        }

    }

    

   
}
