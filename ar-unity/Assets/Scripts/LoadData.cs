using UnityEngine;
using System.Collections;
using System.IO;

public class LoadData : MonoBehaviour 
{

    private bool mLoaded;
    private DataSet mDataset;
    string persistentXmlPath;
    ImageTracker tracker;

    //EXTRAER MULTIMEDIA
    #if UNITY_ANDROID //&& !UNITY_EDITOR

    void Start()
    {
        mLoaded = false;
        mDataset = null;
        persistentXmlPath = Application.persistentDataPath + "/museoepn.xml";

        //if (File.Exists(persistentXmlPath))
        //{
        //    //ResourceManager.Instance.IsDataExtracted = true;
        //    Debug.Log("LOAD DATA: file already exists: " + persistentXmlPath);
        //}
        //else
        //{
        //    Debug.Log("LOAD DATA: Problem in xml file, please verify, failed load dataset");
        //}
        //Debug.Log(gameObject.name + " ResourceManager.Instance.IsDataExtracted: " + ResourceManager.Instance.IsDataExtracted);
        ////Debug.Log(gameObject.name + " ResourceManager.Instance.IsDataWritted: " + ResourceManager.Instance.IsDataWritted);
    }
    // Update is called once per frame
    void Update()
    {
        if (QCARRuntimeUtilities.IsQCAREnabled() && !mLoaded)
        {
            if (ResourceManager.Instance.IsDataExtracted)//&& ResourceManager.Instance.IsDataWritted
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
                    ResourceManager.Instance.IsDataLoad = true;
                    Debug.Log("Unity AR scene, xml loaded: " + persistentXmlPath);
                }
                else
                {
                    Debug.LogError("Failed to load dataset!");
                }
            }
            
        }
    }

    #endif
}
