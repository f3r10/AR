using UnityEngine;
using System.Collections;

public class LoadData : MonoBehaviour 
{

    private bool mLoaded;
    private DataSet mDataset;
    string persistentXmlPath;
    ImageTracker tracker;

    #if UNITY_ANDROID && !UNITY_EDITOR

    void Start()
    {
        mLoaded = false;
        mDataset = null;
        persistentXmlPath = Application.persistentDataPath + "/museoepn.xml";
    }
    // Update is called once per frame
    void Update()
    {
        if (QCARRuntimeUtilities.IsQCAREnabled() && !mLoaded)
        {

            if (mDataset == null)
            {
                // First, create the dataset
                tracker = TrackerManager.Instance.GetTracker<ImageTracker>();
                mDataset = tracker.CreateDataSet();

            }

            if (mDataset.Load(persistentXmlPath, QCARUnity.StorageType.STORAGE_ABSOLUTE))
            {
                tracker.ActivateDataSet(mDataset);
                mLoaded = true;
                
                Debug.Log("Unity AR scene, xml loaded: " + persistentXmlPath);
            }
            else
            {
                Debug.LogError("Failed to load dataset!");
            }
        }
    }

    #endif
}
