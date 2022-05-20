package com.example.testejson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String jsonFilmes="{\"filmes\": "+
            " [" +
            "  {\"nome\": \"X-MEN\","+
            "   \"ano\":2000," +
            "   \"genero\":\"Ação\"" +
            "  },"+
            "  {\"nome\": \"Tomates assassinos\","+
            "   \"ano\":1993," +
            "   \"genero\":\"Terror\"" +
            "  },"+
            "  {\"nome\": \"Palhaços assassinos do espaço sideral\","+
            "   \"ano\":1998," +
            "   \"genero\":\"Comédia\"" +
            "  },"+
            " ]"+
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar carregando = (ProgressBar) findViewById(R.id.progressBar);

        Button botao = (Button) findViewById(R.id.btnUsuario);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IRetrofitUsuarioGit gitUsuario = IRetrofitUsuarioGit.retrofit.create(
                        IRetrofitUsuarioGit.class);
                final Call<Usuario> call = gitUsuario.getUsuario("inducer");
//                try {
//                    Usuario usuario = call.execute().body();
//                    Log.d("MainActivity",usuario.nome);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        int codigo = response.code();
                        if (codigo==200){
                            Usuario usuario = response.body();
                            Log.d("MainActivity", usuario.nome);
                        }else {
                            Log.d("MainActivity", "Erro "+ String.valueOf(codigo));
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Log.d("MainActivity", t.getMessage());
                    }
                });
            }
        });

//        //Codigo que faz parse do json criado acima
//        JSONObject filmes = null;
//        try {
//            filmes = new JSONObject(jsonFilmes);
//            JSONArray arrayFilmes = filmes.getJSONArray("filmes");
//            for (int i=0;i<arrayFilmes.length();i++){
//                JSONObject filme = arrayFilmes.getJSONObject(i);
//                Log.d("MainActivity", filme.getString("nome"));
//                Log.d("Main Activity", Integer.toString(filme.getInt("ano")));
//                Log.d("MainActivity", filme.getString("genero"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //Clique do botão que puxa os registros
        Button botaoSeg = (Button) findViewById(R.id.btnSeguidores);
        botaoSeg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                IRetrofitUsuarioGit gitUsuario = IRetrofitUsuarioGit.retrofit.create(
                        IRetrofitUsuarioGit.class);
            final Call<List<Usuario>> call = gitUsuario.getSeguidores("inducer");

            //Manda exibir a barra de loading
            carregando.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    //Se a barra estiver ativa, desativa-a!
                    if(carregando.isAnimating()){
                        carregando.setVisibility(View.GONE);
                    }
                    List<Usuario> lista = response.body();
                    for (Usuario usuario: lista){
                        Log.d("MainActivity", usuario.login);
                    }
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {

                }
            });
            }
        });
    }
}