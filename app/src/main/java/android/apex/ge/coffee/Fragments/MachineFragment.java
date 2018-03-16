package android.apex.ge.coffee.Fragments;


import android.apex.ge.coffee.R;
import android.apex.ge.coffee.Retrofit.CoffeeService;
import android.apex.ge.coffee.Retrofit.Repo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nika on 14/03/2018.
 * This is a fragment class that is displayed in navigation drawer when corresponding item is
 * clicked in the menu.
 */

public class MachineFragment extends Fragment {

    protected final String LOG_TAG = "MachineFragment";
    private TextView textView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MachineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreateView method of the MachineFragment class");

        View view = inflater.inflate(R.layout.fragment_machine, container, false);

        textView = view.findViewById(R.id.text_view_for_machine);
        textView.setText("This is the machine");

        FloatingActionButton fab = view.findViewById(R.id.fab_fragment_machine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://api.github.com/")
                        .build();
                CoffeeService service = retrofit.create(CoffeeService.class);
                Call<List<Repo>> repos = service.listRepos("nkhar");
                repos.enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        Log.d(LOG_TAG,response.code()+"");

                        String displayResponse = "";

                        List<Repo> repo = response.body();
                        displayResponse+="\n    " + repo.size() + " \n";
                        for(Repo rep : repo){
                            displayResponse+=rep.toString();
                            Log.d(LOG_TAG, rep.getDefaultBranch().toString());

                        }
                        textView.setText(displayResponse);
                        textView.setMovementMethod(new ScrollingMovementMethod());

                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });






        return view;
    }

}
