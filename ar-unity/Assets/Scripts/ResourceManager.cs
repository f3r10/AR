using UnityEngine;
using System.Collections;

public class ResourceManager
{
    #region SINGLETON IMPLEMENTATION
    private static ResourceManager instance = null;

    protected ResourceManager() { }

    public static ResourceManager Instance
    {
        get
        {
            if (instance == null)
                instance = new ResourceManager();
 
            return instance;
        }
    }
    #endregion // SINGLETON IMPLEMENTATION
    // Change in OnTrackingFound and Options Menu
    private string languageInterface = "spa";
    private string nameARObject;

    // Query DB
    private string descriptionText;
    private int numberAudios;      
    private int numberGames;   
    private int numberImages;   
    private int numberVideos;
    private bool isGamePlaying = false;
    private bool isMultimediaReproducing = false;
    
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

    public bool IsGamePlaying
    {
        get { return isGamePlaying; }
        set { isGamePlaying = value; }
    }

    public bool IsMultimediaReproducing
    {
        get { return isMultimediaReproducing; }
        set { isMultimediaReproducing = value; }
    }

    #endregion // PUBLIC METHODS
}
