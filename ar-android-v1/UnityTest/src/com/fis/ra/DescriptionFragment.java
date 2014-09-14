package com.fis.ra;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DescriptionFragment extends Fragment {
	static String msg = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse metus ligula, hendrerit eu turpis at, consectetur varius lorem. Aenean justo sem, aliquam in gravida quis, scelerisque sagittis felis. Vestibulum eget urna sit amet nulla viverra rutrum ut eget est. Vestibulum ac tempor eros. Suspendisse potenti. Sed sed pharetra sapien. Aenean placerat magna augue, vitae faucibus nibh tincidunt vel. Nulla consequat est magna, vel finibus dui pellentesque a. Donec aliquet erat id nisi vehicula fermentum. Cras nec suscipit erat. Etiam nec erat eleifend, pulvinar mauris vel, tristique velit. Vivamus tincidunt cursus odio sagittis condimentum. Cras sit amet facilisis tellus, eu dignissim nibh. Vivamus orci dui, mollis nec placerat in, facilisis non metus.Duis ac semper sapien. Praesent sodales libero at tortor sagittis, ut viverra leo ullamcorper. Vivamus quis augue sed sapien finibus consectetur in tincidunt arcu. Suspendisse ac libero a est dapibus scelerisque. Sed feugiat sem et orci interdum, non iaculis sem semper. Suspendisse sed rhoncus est. Maecenas a sapien lorem. Suspendisse potenti. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus est tellus, molestie non nisl id, condimentum posuere nulla. Proin at malesuada felis. Aliquam hendrerit auctor metus et porta. Curabitur ultrices, lorem nec pellentesque maximus, lacus augue vestibulum tellus, a convallis libero dolor sollicitudin purus. Nam eleifend dolor elit, ut molestie risus iaculis at. Pellentesque tempus sollicitudin lectus vitae gravida. Cras non sapien ac libero aliquam scelerisque a non est. Nulla facilisi. Ut id tortor sollicitudin, feugiat sem vitae, convallis arcu. Sed pretium hendrerit turpis, in egestas nisi tincidunt non. Cras gravida nisl mauris, non blandit metus consectetur eu. Proin lectus elit, imperdiet et nunc id, viverra ullamcorper dui. Donec consectetur placerat augue id viverra. Nullam euismod arcu lacinia urna imperdiet, eu iaculis felis blandit. Etiam sodales mollis ipsum vel suscipit.";
	static TextView msgFromUnity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_description,
				container, false);
		msgFromUnity = (TextView) rootView.findViewById(R.id.txtUnity);
		msgFromUnity.setText(msg);

		return rootView;
	}

	public static void setmyText(final String string) {
		Log.d("Prueba de mensajes", string);
	}

	public static void setMsgUnity(String string) {
		// msgFromUnity.setText(string);
		msg = string;
		Log.d("settings", string);
	}
}
