using UnityEngine;
using System.Collections;

public class ResourceManager
{
    #region OLD SINGLETON IMPLEMENTATION
    /*
    private static ResourceManager instance = null;

    protected ResourceManager() { Debug.Log("START SINGLETON"); }

    public static ResourceManager Instance
    {
        get
        {
            if (instance == null)
                instance = new ResourceManager();
 
            return instance;
        }
    }
     */
    #endregion // OLD SINGLETON IMPLEMENTATION

    #region NEW SINGLETON IMPLEMENTATION

    private static volatile ResourceManager instance;
    private static object _objToLock = new Object();

    private ResourceManager() { Debug.Log("START SINGLETON"); }

    public static ResourceManager Instance
    {
        get
        {
            if (instance == null)
            {
                lock (_objToLock)
                {
                    if (instance == null)
                        instance = new ResourceManager();
                }
            }
            return instance;
        }
    }

    #endregion // NEW SINGLETON IMPLEMENTATION

    // Change in OnTrackingFound and Options Menu
    private string languageInterface = "spa";
    private string nameARObject = "";

    // Query DB
    private string descriptionText;
    private int numberAudios;      
    private int numberGames;   
    private int numberImages;   
    private int numberVideos;

    private bool isGameStarted = false;
    private bool isGamePaused = false;
    
    private bool isMultimediaStarted = false;// started
    private bool isMultimediaPaused = false;

    private bool isObjectRecognized = false;

    private bool isDataExtracted = true;//true for test, is false
    private bool isDataWritted = false;//TAL VEZ OMITIR
    private bool isDataLoad = false;   
   
    #region PUBLIC METHODS
    public string DescriptionText
    {
        get { return descriptionText; }
        set { descriptionText = value; }
    }

    public string LanguageInterface
    {
        get { return languageInterface; }
        set { languageInterface = value; }
    }

    public string NameARObject
    {
        get { return nameARObject; }
        set { nameARObject = value; }
    }

    public int NumberAudios
    {
        get { return numberAudios; }
        set { numberAudios = value; }
    }

    public int NumberGames
    {
        get { return numberGames; }
        set { numberGames = value; }
    }

    public int NumberImages
    {
        get { return numberImages; }
        set { numberImages = value; }
    }

    public int NumberVideos
    {
        get { return numberVideos; }
        set { numberVideos = value; }
    }

    public bool IsGameStarted
    {
        get { return isGameStarted; }
        set { isGameStarted = value; }
    }

    public bool IsGamePaused
    {
        get { return isGamePaused; }
        set { isGamePaused = value; }
    }

    public bool IsMultimediaStarted
    {
        get { return isMultimediaStarted; }
        set { isMultimediaStarted = value; }
    }

    public bool IsMultimediaPaused
    {
        get { return isMultimediaPaused; }
        set { isMultimediaPaused = value; }
    }

    public bool IsObjectRecognized
    {
        get { return isObjectRecognized; }
        set { isObjectRecognized = value; }
    }

    public bool IsDataExtracted
    {
        get { return isDataExtracted; }
        set { isDataExtracted = value;}
    }

    public bool IsDataWritted
    {
        get { return isDataWritted; }
        set { isDataWritted = value; }
    }

    public bool IsDataLoad
    {
        get { return isDataLoad; }
        set { isDataLoad = value; }
    }

    #endregion // PUBLIC METHODS


}
