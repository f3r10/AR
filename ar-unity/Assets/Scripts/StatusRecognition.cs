using UnityEngine;
using System.Collections;

public class StatusRecognition : MonoBehaviour {

	// Use this for initialization
	void Start () {
        GetStatusRecognition();
	}


    public void GetStatusRecognition()
    {
        // 1. Start app, in Android compare and show toast
        if (ResourceManager.Instance.NameARObject.Equals(""))
        {
            CallMobileMethod("StatusRecognitionStart","");
        }
    }

    private void CallMobileMethod(string methodName, params object[] args)
    {
        #if UNITY_ANDROID && !UNITY_EDITOR
        using (AndroidJavaClass androidJavaClass = new AndroidJavaClass("com.fis.ra"))
        {
            androidJavaClass.CallStatic(methodName,args);
        }
        #endif

        Debug.Log(methodName + ":" + gameObject.name);
    }
}
