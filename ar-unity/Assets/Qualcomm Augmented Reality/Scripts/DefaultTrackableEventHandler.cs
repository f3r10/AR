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
    #region PRIVATE_MEMBER_VARIABLES
 
    private TrackableBehaviour mTrackableBehaviour;
    Renderer[] rendererComponents;
    string idTargetDataUnity;
    // use only is necessary
    // Collider[] colliderComponents;

    #endregion // PRIVATE_MEMBER_VARIABLES



    #region UNTIY_MONOBEHAVIOUR_METHODS
    
    void Start()
    {
        idTargetDataUnity = "";
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
    public void OnTrackableStateChanged(
                                    TrackableBehaviour.Status previousStatus,
                                    TrackableBehaviour.Status newStatus)
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
    
    #endregion // PUBLIC_METHODS



    #region PRIVATE_METHODS


    private void OnTrackingFound()
    {
        // if old targer is equals to new target, do nothig, else if is no equals
        // two cases: start app or recognize new target (diferent to old target)
        if (!idTargetDataUnity.Equals(mTrackableBehaviour.TrackableName))
        {
            idTargetDataUnity = mTrackableBehaviour.TrackableName;

            rendererComponents = GetComponentsInChildren<Renderer>(true);

            // use only is necessary
            // colliderComponents = GetComponentsInChildren<Collider>(true);

            // Enable rendering:
            foreach (Renderer component in rendererComponents)
            {
                component.enabled = true;

                // use only videogames
                // component.gameObject.SetActive(true);
            }

            // use only is necessary
            // Enable colliders:
            //foreach (Collider component in colliderComponents)
            //{
            //    component.enabled = true;
            //}


            // For debug
            #if UNITY_ANDROID && !UNITY_EDITOR
            ShowMobileToast("SetIdTarget", idTargetDataUnity);
            #endif


            #if UNITY_EDITOR
            Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " found");
            #endif
            //CONSULTA DB
        } 

    }

    private void OnTrackingLost()
    {
        // 1. inicializated application
        if (idTargetDataUnity.Equals(""))
        {
            //call to android, show toast "no focus"
            ShowMobileToast("ShowToastNoFocus");


            // For debug
            #if UNITY_EDITOR
            Debug.Log("Focus camera to object");
            #endif
        } 
        else
        {
            // 2. tracking lost after tracking on
            ShowMobileToast("ShowToastTrackingLost",idTargetDataUnity);
            
            // For debug
            #if UNITY_EDITOR
            Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " lost");
            #endif
            //FALTAN ACCIONES
        }

        //PARA GAMEOBJECTS U OBJETOS EN 3D

        rendererComponents = GetComponentsInChildren<Renderer>(true);

        // use only is necessary
        // colliderComponents = GetComponentsInChildren<Collider>(true);

        // Disable rendering:
        foreach (Renderer component in rendererComponents)
        {
            component.enabled = false;

            // use only videogames
            // component.gameObject.SetActive(false);
        }

        // use only is necessary
        // Disable colliders:
        //foreach (Collider component in colliderComponents)
        //{
        //    component.enabled = false;
        //}

        
    }

    // methods for toast, preprocessor directives for all platforms (only android for this project)

    private void ShowMobileToast(string methodName)
    {
        #if UNITY_ANDROID
        using (AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.fis.ra"))
        {
            androidJavaClass.CallStatic(methodName);
        }
        #endif   
    }

    private void ShowMobileToast(string methodName, params object[] args)
    {
        #if UNITY_ANDROID
        using (AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.fis.ra"))
        {
            androidJavaClass.CallStatic(methodName,args);
        }
        #endif
    }

    #endregion // PRIVATE_METHODS
}
