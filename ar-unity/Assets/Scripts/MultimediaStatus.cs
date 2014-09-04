using UnityEngine;
using System.Collections;

public class MultimediaStatus : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	
	public void SetMultimediaStartedStatus ( bool status) 
    {
        ResourceManager.Instance.IsMultimediaStarted = status;
	}

    public void SetMultimediaPausedStatus(bool status)
    {
        ResourceManager.Instance.IsMultimediaPaused = status;
    }
}
