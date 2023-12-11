package defi1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class Main {
    public static void main(String[] args) throws IOException {
    requeteAPIallVilles();
    }


    public static void requeteAPIallVilles() throws IOException {
        ;
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
            System.out.println(communeListPop.size());
            System.out.println("elements: ");
            communeListPop.forEach(elem -> System.out.print(elem.get("nom") + " :  " + elem.get("population")+" / \n"));


        }
    }
}
