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

        StartCoroutine(ExtractAllData());
        
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

    IEnumerator ExtractAllData()
    {
        //LUEGO HACER ESTO CON UN FOR
        yield return StartCoroutine(StreamMultimediaResource(streamingXmlPath, persistentXmlPath));

        yield return StartCoroutine(StreamMultimediaResource(streamingDatPath, persistentDatPath));

        Debug.Log("ALL DATA EXTRACTED");
        ResourceManager.Instance.IsDataExtracted = true;

        Application.LoadLevel(1);
    }

    
    #endif


    // #if UNITY_EDITOR

    void Awake()
    {
        //ResourceManager.Instance.IsDataExtracted = true;
        
        //Application.LoadLevel(1);

////        //DATABASE TEST
////        // Query DataBase
////        SqliteDatabase sqlDB = new SqliteDatabase("ResourcesDB.db");
////        DataTable resultsTable;

////        resultsTable = sqlDB.ExecuteQuery(@"SELECT name_ARobject
////                                            FROM AR_Object");

////        Debug.Log(resultsTable.Rows.Count);
    }


   // #endif


    private IEnumerator StreamMultimediaResource(string streamingResourcePath, string persistentResourcePath)
    {
        using (WWW wwwMultimedia = new WWW( streamingResourcePath))
        {
            yield return wwwMultimedia;

            if( wwwMultimedia.isDone && string.IsNullOrEmpty(wwwMultimedia.error))
            {
                if(File.Exists( persistentResourcePath ) )
                {
                    Debug.Log( gameObject.name+" file already exists: " + persistentResourcePath );
                }
                else
                {
                    File.WriteAllBytes(persistentResourcePath, wwwMultimedia.bytes);
                   
                    if(File.Exists(persistentResourcePath) )
                    {
                        Debug.Log(gameObject.name + " SUCCESS: File written! " + persistentResourcePath);
                    }
                    else
                    {
                        Debug.Log(gameObject.name + " PROBLEM WRITTING " + persistentResourcePath);
                    }
                }
            }
            else
            {
                Debug.Log(gameObject.name + " PROBLEM LOAD " + persistentResourcePath);
            }
        }
    }
}
