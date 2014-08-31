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

    // use only is necessary
    // Collider[] colliderComponents;

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
                SetIdTarget();
        #endif

        Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " found");
    }


    private void OnTrackingLost()
    {
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

        Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " lost");
    }

    private void SetIdTarget()
    {
        #if UNITY_ANDROID

        using (AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.fis.ra"))
        {
            androidJavaClass.SetStatic("idTargetData", mTrackableBehaviour.TrackableName);

        }

        #endif

        //preprocessor directives for other platforms:
    }

    #endregion // PRIVATE_METHODS
}
