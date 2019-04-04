package com.example.adndi___project2.recipe_utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import timber.log.Timber;

/**
 * Classe com os metodos que irão trabalhar com o a conexão de internet
 */
public class NetworkUtilities {

    // Codigo de resposta ok da conexão
    private static final int URL_CONNECTION_GET_RESPONSE_CODE = 200;

    /**
     * readFromStream é utilizado para lermos o fluxo de informações da Conexão e criarmos o JSON
     *
     * @param inputStream fluxo de informação da conexão aberta
     * @return a String com o JSON retornado
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * getResponseFromHttpUrl é utilizado para abrirmos uma conexão com o respectivo URL e retornar
     * o JSON com as informações
     *
     * @param recipeUrlString URL criado pelas opções do usuario
     * @return a String com o JSON retornado
     */
    public static String getResponseFromHttpUrl(String recipeUrlString) throws IOException {
        String jsonResponse = "";

        URL recipeUrl = null;

        Uri.Builder recipeUrlBuild = Uri.parse(recipeUrlString).buildUpon();

        try {
            recipeUrl = new URL(recipeUrlBuild.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (recipeUrl == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) recipeUrl.openConnection();
            urlConnection.connect();
            if (urlConnection.getResponseCode() == URL_CONNECTION_GET_RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Timber.e("Error response code: %s", urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Timber.e(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * testConnection Metodo que testará ser a conexão está ativa
     *
     * @return Boolean mostrando se a conexão foi bem sucedida
     */
    static public Boolean testConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Boolean connected = (networkInfo != null && networkInfo.isConnected());
        Timber.d( "testConnection: %s", connected );
        return connected;
    }

}
