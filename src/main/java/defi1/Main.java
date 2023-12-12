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
import defi1.framework.common.State;

public class Main {
    public static void main(String[] args) throws IOException {
        StringBuilder response = requeteAPIallVilles();
        List<Map<String, Object>> list = convertirJSONToList(response);
        construireProbleme(list);
    }


    public static StringBuilder requeteAPIallVilles() throws IOException {
        URL url = new URL("https://geo.api.gouv.fr/communes");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responsecode = con.getResponseCode();
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {

            // Obtenez la réponse de l'API
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            con.disconnect();
            return response;
        }

    }

    public static List<Map<String,Object>> convertirJSONToList(StringBuilder response) throws JsonProcessingException {
        //convertir la réponse json en liste d'objet
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> communeList = objectMapper.readValue(response.toString(), new TypeReference<List<Map<String, Object>>>() {});

        System.out.println(communeList.get(0).get("population"));


        // Triez la liste en fonction de la population (supposons que l'attribut de population s'appelle "population")
        communeList.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Object pop1 =  o1.get("population");
                Object pop2 = o2.get("population");
                if(pop1 == null) {
                    pop1 = -1;
                }
                if(pop2 == null) pop2 = -1;
                return -Integer.compare((Integer) pop1, (Integer)pop2);
            }
        });

        List<Map<String, Object>> communeListPop = communeList.subList(0, 101);
       //System.out.println(communeListPop.size());
        //System.out.println("elements: ");
       //communeListPop.forEach(elem -> System.out.print(elem.get("nom") + " :  " + elem.get("population")+" / \n"));
        return communeListPop;
    }

    public static void construireProbleme(List<Map<String, Object>> communeListPop) throws IOException {
        List<State> states = new ArrayList<>(100);
        String nom;
        int population;
        double[] coord;
        for(Map<String, Object> commune : communeListPop){
            nom = (String) commune.get("nom");
            population = (int) commune.get("population");
            JsonNode jsonNode = requeteOneVille(nom);
            JsonNode coordinates = jsonNode.get("features").get(0).get("geometry").get("coordinates");
            double latitude = coordinates.get(0).asDouble();
            double longitude = coordinates.get(1).asDouble();

            // Affichage des coordonnées
            System.out.println("Latitude: " + latitude);
            System.out.println("Longitude: " + longitude);

        }
    }
    public static JsonNode requeteOneVille(String nom) throws IOException {
        nom = normaliserNom(nom);
        URL url = new URL("https://api-adresse.data.gouv.fr/search/?q=" + nom);
        System.out.println("url : " + url.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responsecode = con.getResponseCode();
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {

            // Obtenez la réponse de l'API
            InputStream inputStream = con.getInputStream();
            if ("gzip".equals(con.getContentEncoding())) {
                inputStream = new GZIPInputStream(inputStream);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            con.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            return jsonNode;
        }

    }

    public static String normaliserNom(String nom) {
        // Supprimer les accents et normaliser le texte
        nom = Normalizer.normalize(nom, Normalizer.Form.NFD);
        nom = nom.replaceAll("[^\\p{ASCII}]", "");

        // Remplacer les espaces par des plus
        nom = nom.replaceAll(" ", "+");

        return nom;
    }

}
