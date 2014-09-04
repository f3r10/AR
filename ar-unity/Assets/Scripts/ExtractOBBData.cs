using UnityEngine;
using System.Collections;
using System.IO;
using System.Text;

public class ExtractOBBData : MonoBehaviour 
{

    private string streamingXmlPath;
    private string streamingDatPath;
    private string streamingMultimediaPath;

    private string persistentXmlPath;
    private string persistentDatPath;
    private string persistentMultimediaPath;

    private bool isLoadAllData = false;
    private bool isLoadXml = false;
    private bool isLoadDat = false;

    private WWW wwwXml;
    private WWW wwwDat;
    //private WWW wwwMultimedia;

    private int arObjectNumber;

    // This is for test in unity3d editor
    #if UNITY_ANDROID //&& !UNITY_EDITOR

    void Start()
    {
        #region EXTRACT VUFORIA FILES

        streamingXmlPath = Application.streamingAssetsPath + "/QCAR/museoepn.xml";
        streamingDatPath = Application.streamingAssetsPath + "/QCAR/museoepn.dat";

        persistentXmlPath = Application.persistentDataPath + "/museoepn.xml";
        persistentDatPath = Application.persistentDataPath + "/museoepn.dat";

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
        #endregion // EXTRACT VUFORIA FILES

        /*
        #region QUERY DATABASE

        // Query DataBase
        SqliteDatabase sqlDB = new SqliteDatabase("ResourcesDB.db");
        DataTable resultsTable;

        resultsTable = sqlDB.ExecuteQuery(@"SELECT name_ARobject
                                            FROM AR_Object");

        arObjectNumber = resultsTable.Rows.Count;

        #endregion // QUERY DATABASE

        #region EXTRACT AUDIO FILES
        
        // Streaming Audio Resources (NO LANGUAGE)      
        for (int i = 0; i < arObjectNumber; i++ )
        {
            // add +"/Audio/"+ in persistmultimediapath
            streamingMultimediaPath = Application.streamingAssetsPath + "/Audio/" + resultsTable.Rows[i]["name_ARobject"].ToString() + "_audio.mp3";
            persistentMultimediaPath = Application.persistentDataPath + resultsTable.Rows[i]["name_ARobject"].ToString() + "_audio.mp3";
            
            StartCoroutine(StreamMultimediaResource(streamingMultimediaPath,persistentMultimediaPath));
        }

        #endregion // EXTRACT AUDIO FILES        
        */
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
                isLoadAllData = true;
                // FOR TEST, DISABLE
                Application.LoadLevel(1);
            }

        }

    }

    
    #endif

    #if UNITY_EDITOR

    void Awake()
    {
       // Application.LoadLevel(1);

//        //DATABASE TEST
//        // Query DataBase
//        SqliteDatabase sqlDB = new SqliteDatabase("ResourcesDB.db");
//        DataTable resultsTable;

//        resultsTable = sqlDB.ExecuteQuery(@"SELECT name_ARobject
//                                            FROM AR_Object");

//        Debug.Log(resultsTable.Rows.Count);
    }

    #endif

    private IEnumerator StreamMultimediaResource(string streamingResourcePath, string persistentResourcePath)
    {
        using (WWW wwwMultimedia = new WWW( streamingResourcePath))
        {
            yield return wwwMultimedia;

            if( wwwMultimedia.isDone)// VER METODO EN LA BSE DE DATOS NULL OR EMPTY
            {
                if(File.Exists( persistentResourcePath ) )
                {
                    Debug.Log( "file already exists: " + persistentResourcePath );
                }
                else
                {
                    File.WriteAllBytes(persistentResourcePath, wwwMultimedia.bytes);
                   
                    if( System.IO.File.Exists(persistentResourcePath) )
                    {
                        Debug.Log("SUCCESS: File written! " + persistentResourcePath);
                    }
                }
            }
        }
    }
}
