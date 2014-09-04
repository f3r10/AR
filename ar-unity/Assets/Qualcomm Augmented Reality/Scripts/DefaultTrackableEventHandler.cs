/*==============================================================================
Copyright (c) 2010-2014 Qualcomm Connected Experiences, Inc.
All Rights Reserved.
Confidential and Proprietary - Qualcomm Connected Experiences, Inc.
==============================================================================*/

using UnityEngine;

/// <summary>
/// A custom handler that implements the ITrackableEventHandler interface.
/// </summary>
public class DefaultTrackableEventHandler : MonoBehaviour,
                                            ITrackableEventHandler
{
    #region PUBLIC_MEMBER_VARIABLES

    public GameObject AugmentedRealityGame;
    public GameObject DataBase;

    #endregion // PUBLIC_MEMBER_VARIABLES

    #region PRIVATE_MEMBER_VARIABLES
 
    private TrackableBehaviour mTrackableBehaviour;
    Renderer[] rendererComponents;

    #endregion // PRIVATE_MEMBER_VARIABLES

    #region UNTIY_MONOBEHAVIOUR_METHODS
    
    void Start()
    {

        mTrackableBehaviour = GetComponent<TrackableBehaviour>();
        if (mTrackableBehaviour)
        {
            mTrackableBehaviour.RegisterTrackableEventHandler(this);
        }
    }

    #endregion // UNTIY_MONOBEHAVIOUR_METHODS

    #region PUBLIC_METHODS

    /// <summary>
    /// Implementation of the ITrackableEventHandler function called when the
    /// tracking state changes.
    /// </summary>
    public void OnTrackableStateChanged(TrackableBehaviour.Status previousStatus, TrackableBehaviour.Status newStatus)
    {
        if (newStatus == TrackableBehaviour.Status.DETECTED ||
            newStatus == TrackableBehaviour.Status.TRACKED ||
            newStatus == TrackableBehaviour.Status.EXTENDED_TRACKED)
        {
            OnTrackingFound();
        }
        else
        {
            OnTrackingLost();
        }
    }

    // Set language (option menu, in Android pass 2 parameters: ARname object and language)
    public void SetSingletonLanguage(string language)
    {
        ResourceManager.Instance.LanguageInterface = language;
        LoadDataObject();
    }

    // Load and set data of ar object recongnized
    public void LoadDataObject()
    {
        // 1. Stop game
        if (ResourceManager.Instance.IsGameStarted)
        {
            AugmentedRealityGame.SetActive(false);
        }

        // 2. Stop multimedia and go to Description o AR Fragment (SEE IN ANDROID)
        if (ResourceManager.Instance.IsMultimediaStarted)
        {
            //go to other fragment
            CallMobileMethod("StopMultimediaPlaying");
        }

        // 3. Set trackable name
        ResourceManager.Instance.NameARObject = mTrackableBehaviour.name;

        // 4. Query DB
        DataBase.GetComponent<QueryDatabase>().GetNumberOfResources(ResourceManager.Instance.NameARObject,
                                                                    ResourceManager.Instance.LanguageInterface);

        // 5. Render 3D objects and active children gameobject

        rendererComponents = GetComponentsInChildren<Renderer>(true);

        foreach (Renderer component in rendererComponents)
        {
            component.enabled = true;

            // use only videogames
            component.gameObject.SetActive(true);
        }

        Debug.Log("Load Trackable " + mTrackableBehaviour.TrackableName + " data ok");
    }

    #endregion // PUBLIC_METHODS

    #region PRIVATE_METHODS


    private void OnTrackingFound()
    {
        // 1. Inicializated application
        // 1.1 is gameobject new? YES
        if (!ResourceManager.Instance.NameARObject.Equals(mTrackableBehaviour.TrackableName))
        {
            // 1.1.1 is game started? YES
            if (ResourceManager.Instance.IsGameStarted)
            {
                // se supone que lo esta jugando ya que esto es true si el juego esta en pausa o play, solo al
                // salir o antes de iniciar se pone en false

                // 1.1.1.1 pause game
                if (ResourceManager.Instance.IsGamePaused)
                {
                    // AugmentedRealityGame.pauseGame();
                }

                // 1.1.1.2 call Android Dialog
                // Two dialogs, but unity call one
                // Play sound
                // Send gameObject name to back to this gameobject, script and LoadDataobject method
                CallMobileMethod("ShowDialogLoadDataNewObject", gameObject.name);
            } 
            // NO (game not started)
            else
            {
                // 1.1.1.3 call Android Dialog
                // Not need pause media
                if (ResourceManager.Instance.IsMultimediaPaused || ResourceManager.Instance.IsMultimediaStarted)
                {
                    CallMobileMethod("ShowDialogLoadDataNewObject", gameObject.name);
                } 
                // 1.1.1.3 Load data
                else
                {
                    LoadDataObject();
                }
            }

        }
        // NO (recognize the same object)
        else
        {
            //AugmentedRealityGame.playGame();

            // 1.1.2 is game no started?
            if (!ResourceManager.Instance.IsGameStarted)
            {
                AugmentedRealityGame.SetActive(true);
                CallMobileMethod("ShowToastCanPlayGame");
                
            }
        }

        Debug.Log("New Trackable " + mTrackableBehaviour.TrackableName + " found");
    }

    private void OnTrackingLost()
    {
        rendererComponents = GetComponentsInChildren<Renderer>(true);

        // 1. Inicializated application
        if (ResourceManager.Instance.NameARObject.Equals(""))
        {
            // 1.1 Call to android, show toast "no focus"
            CallMobileMethod("ShowToastNoFocus");
        }
        
        // 2. tracking lost after tracking on
        
        // 2.1 is game Started? NO
        if (!ResourceManager.Instance.IsGameStarted)
        {
            // 2.1.1 Disable videogame (in future, disable in foreach and delete public variable)
            AugmentedRealityGame.SetActive(false);

            // 2.1.2 Show toast message request focus on the object
            CallMobileMethod("ShowToastTrackingLost");
        }

        // 2.2 is game paused? YES
        if (ResourceManager.Instance.IsGamePaused)
        {
            // 2.2.1 Show toast message request focus on the object
            CallMobileMethod("ShowToastTrackingLost");
        }
        // is game paused? NO
        else
        {
            //AugmentedRealityGame.pauseGame();
            CallMobileMethod("ShowToastTrackingLost");
        }

        // 3. No render objects

        rendererComponents = GetComponentsInChildren<Renderer>(true);

        foreach (Renderer component in rendererComponents)
        {
            component.enabled = false;
        }

        Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " lost");
    }

    // methods for toast and dialogs, preprocessor directives for all platforms (only android for this project)

    private void CallMobileMethod(string methodName)
    {
        #if UNITY_ANDROID && !UNITY_EDITOR
        using (AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.fis.ra"))
        {
            androidJavaClass.CallStatic(methodName);
        }
        #endif

        Debug.Log(methodName);
    }

    private void CallMobileMethod(string methodName, params object[] args)
    {
        #if UNITY_ANDROID && !UNITY_EDITOR
        using (AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.fis.ra"))
        {
            androidJavaClass.CallStatic(methodName,args);
        }
        #endif

        Debug.Log(methodName);
    }

    #endregion // PRIVATE_METHODS
}
