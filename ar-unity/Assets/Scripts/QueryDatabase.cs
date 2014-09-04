using UnityEngine;
using System.Collections;
using System;

public class QueryDatabase : MonoBehaviour 
{
    // SET AND SEND NUMBER OF RESOURCES TO ANDROID

	void Start () 
    {
        //FOR TEST
        //GetNumberOfResources("ar_001","spa");
	}

    // This function work in OnTrackingFound and Option menu
    public void GetNumberOfResources(string arObject,string language)
    {
        SqliteDatabase sqlDB = new SqliteDatabase("ResourcesDB.db");
        DataTable resultsTable;

        #region Text of description

        resultsTable = sqlDB.ExecuteQuery(@"SELECT D.description_text
                                            FROM Description D
                                            JOIN AR_Object AR
                                            ON (D.id_ARObject=AR.id)
                                            JOIN Language L
                                            ON (D.id_language=L.id)
                                            WHERE AR.name_ARObject="+"'"+arObject+"'"+
                                            "AND L.languageISO="+"'"+language+"'");

        ResourceManager.Instance.DescriptionText = resultsTable.Rows[0]["description_text"].ToString();
        Debug.Log("DB text of "+ arObject+": "+resultsTable.Rows[0]["description_text"].ToString());
        
        #endregion // Text of description

        #region Number of Audios 
        
        resultsTable = sqlDB.ExecuteQuery(@"SELECT A.number_audio
                                            FROM Audio A
                                            JOIN AR_Object AR
                                            ON (A.id_ARObject=AR.id)
                                            WHERE AR.name_ARObject=" + "'" + arObject + "'");

        ResourceManager.Instance.NumberAudios = int.Parse(resultsTable.Rows[0]["number_audio"].ToString());

        Debug.Log("DB Number of audios of " + arObject + ": " + int.Parse(resultsTable.Rows[0]["number_audio"].ToString()));

        #endregion // Number of Audios

        #region Number of Games
               
        resultsTable = sqlDB.ExecuteQuery(@"SELECT G.number_games
                                            FROM Games G
                                            JOIN AR_Object AR
                                            ON (G.id_ARObject=AR.id)
                                            WHERE AR.name_ARObject=" + "'" + arObject + "'");

        ResourceManager.Instance.NumberGames = int.Parse(resultsTable.Rows[0]["number_games"].ToString());

        Debug.Log("DB Number of games of " + arObject + ": " + int.Parse(resultsTable.Rows[0]["number_games"].ToString()));

        #endregion // Number of Games     

        #region Number of Images
               
        resultsTable = sqlDB.ExecuteQuery(@"SELECT I.number_images
                                            FROM Images I
                                            JOIN AR_Object AR
                                            ON (I.id_ARObject=AR.id)
                                            WHERE AR.name_ARObject=" + "'" + arObject + "'");

        ResourceManager.Instance.NumberImages = int.Parse(resultsTable.Rows[0]["number_images"].ToString());

        Debug.Log("DB Number of images of " + arObject + ": " + int.Parse(resultsTable.Rows[0]["number_images"].ToString()));

        #endregion // Number of Images

        #region Number of Videos

        resultsTable = sqlDB.ExecuteQuery(@"SELECT V.number_video
                                            FROM Video V
                                            JOIN AR_Object AR
                                            ON (V.id_ARObject=AR.id)
                                            WHERE AR.name_ARObject=" + "'" + arObject + "'");

        ResourceManager.Instance.NumberVideos = int.Parse(resultsTable.Rows[0]["number_video"].ToString());

        Debug.Log("DB Number of videos of " + arObject + ": " + int.Parse(resultsTable.Rows[0]["number_video"].ToString()));

        #endregion // Number of Videos

        #region Other member variables
        ResourceManager.Instance.IsGameStarted = false;
        ResourceManager.Instance.IsGamePaused = false;
        ResourceManager.Instance.IsMultimediaStarted = false;
        ResourceManager.Instance.IsMultimediaPaused = false;
        #endregion //Other member variables

        // SEND INFORMATION TO ANDROID       
        SendResourcesData();

    }
    
    // FALTA DEFINIR NOMBRE METODO EN ANDROID
    private void SendResourcesData()
    {
        #if UNITY_ANDROID && !UNITY_EDITOR
        using (AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.fis.ra"))
        {
            androidJavaClass.CallStatic("LoadMultimedia",ResourceManager.Instance.NameARObject
                                                    ,ResourceManager.Instance.DescriptionText
                                                    ,ResourceManager.Instance.NumberAudios
                                                    ,ResourceManager.Instance.NumberGames
                                                    ,ResourceManager.Instance.NumberImages
                                                    ,ResourceManager.Instance.NumberVideos);
        }
        
        #endif
        Debug.Log("Send resources to Android ok ");

        Debug.Log("Singleton:");
        Debug.Log("Language interface: " + ResourceManager.Instance.LanguageInterface);
        Debug.Log("Name AR Object: "+ResourceManager.Instance.NameARObject);
        Debug.Log("Description text: "+ResourceManager.Instance.DescriptionText);
        Debug.Log("Number of audios: "+ResourceManager.Instance.NumberAudios);                                          
        Debug.Log("Number of games: "+ResourceManager.Instance.NumberGames);
        Debug.Log("Number of images: "+ResourceManager.Instance.NumberImages);
        Debug.Log("Number of videos: " + ResourceManager.Instance.NumberVideos);

        Debug.Log("isGameStarted: " + ResourceManager.Instance.IsGameStarted);
        Debug.Log("isGamePaused: " + ResourceManager.Instance.IsGamePaused);
        Debug.Log("isMultimediaStarted: " + ResourceManager.Instance.IsMultimediaStarted);
        Debug.Log("isMultimediaPaused: " + ResourceManager.Instance.IsMultimediaPaused);
        Debug.Log("isObjectRecognized: " + ResourceManager.Instance.IsObjectRecognized);

    }
}
