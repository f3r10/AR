package com.fis.ra;

import com.unity3d.player.UnityPlayer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class DialogoConfirmacion extends DialogFragment {
	private static String objectFound = "empty";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
 
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
 
        builder.setMessage("Se ha detectado un nuevo objeto.\n¿Desea cargar los datos correspondientes?")
        .setTitle("Confirmacion")
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
               public void onClick(DialogInterface dialog, int id) {
                    Log.i("Dialogos", "Confirmacion Aceptada." + objectFound);
                    UnityPlayer.UnitySendMessage(objectFound, "DialogResponse", "ok");
                    dialog.cancel();
                   }
               })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmacion Cancelada.");
                        dialog.cancel();
                   }
               });
 
        return builder.create();
    }
    
    
    public static void setTrackableFound(String  object){
    	objectFound = object;
    }
}
