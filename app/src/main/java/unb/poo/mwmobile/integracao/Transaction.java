package unb.poo.mwmobile.integracao;

import org.json.JSONArray;

import unb.poo.mwmobile.models.User;

/**
 * Interface que tem que ser implementada
 * para receber os dados do servidor
 *
 *
 * Created by Scartezini on 05/11/2015.
 */
public interface Transaction {


    /**
     * Atravez desse metodo o servidor vai mandar um
     * JsonArray para quem deu o requeste
     * nesse array vai esta a resposta do servidor
     *
     * @param jsonArray
     */
    public void doAfter(JSONArray jsonArray);
}