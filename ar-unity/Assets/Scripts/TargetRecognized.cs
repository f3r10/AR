using UnityEngine;
using System.Collections;

public class TargetRecognized : MonoBehaviour, ITrackableEventHandler
{

    //FALTA USAR USING EN LOS OBJETOS ANDROIDJAVA
    //cambiar a ingles

    private TrackableBehaviour imageTarget;
   // AndroidJavaClass androidJavaClass;
   // AndroidJavaObject androidJavaObject;
    //string strMensajeUnity;


    // Use this for initialization
    void Start()
    {
        imageTarget = GetComponent<TrackableBehaviour>();

        //androidJavaClass = new AndroidJavaClass("com.fis.ra");

        if (imageTarget)
        {
            imageTarget.RegisterTrackableEventHandler(this);
        }

    }

    public void OnTrackableStateChanged(TrackableBehaviour.Status estadoPrevio, TrackableBehaviour.Status nuevoEstado)
    {
        if (nuevoEstado == TrackableBehaviour.Status.DETECTED ||
            nuevoEstado == TrackableBehaviour.Status.TRACKED ||
            nuevoEstado == TrackableBehaviour.Status.EXTENDED_TRACKED)
        {
            OnTrackingFound();
        }
        else
        {
            OnTrackingLost();
        }
    }

    private void OnTrackingFound()
    {
        Debug.Log("Unity target recognized: " + imageTarget.TrackableName);

        #if UNITY_ANDROID
        SetIdTarget();
        #endif
    }

    private void OnTrackingLost()
    {
        
    }

    private void SetIdTarget()
    {
        using (AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.fis.ra"))
        {
            androidJavaClass.SetStatic("idTargetData",imageTarget.TrackableName);
            
        }
    }
}
