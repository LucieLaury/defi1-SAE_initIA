package defi1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.Normalizer;
import java.util.*;
import java.util.zip.GZIPInputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import defi1.framework.RequeteAPI;
import defi1.framework.common.State;
import defi1.probleme.Heuristique;
import defi1.probleme.Ville;
import defi1.probleme.VilleState;

public class Main {
    private static Ville ville;
    public static void main(String[] args) throws IOException {

        List<State> states = RequeteAPI.construireStates();
        ville = new Ville(states);
    }






}
