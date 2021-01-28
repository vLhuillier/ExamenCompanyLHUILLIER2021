package com.lhuillier.examen_company_lhuillier.service

import android.util.JsonReader
import android.util.JsonToken
import com.lhuillier.examen_company_lhuillier.data.model.Company
import com.lhuillier.examen_company_lhuillier.data.model.SearchHistory
import com.lhuillier.examen_company_lhuillier.data.model.SearchHistoryWithCompanies
import com.lhuillier.examen_company_lhuillier.data.tier.CompanyDAO
import com.lhuillier.examen_company_lhuillier.data.tier.SearchHistoryDAO
import com.lhuillier.examen_company_lhuillier.data.tier.SearchHistoryWithCompaniesDAO
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection


class CompanyService(private var searchHistoryDAO: SearchHistoryDAO,
                     private var companyDAO: CompanyDAO,
                     private var searchHistoryWithCompaniesDAO: SearchHistoryWithCompaniesDAO) {

    private val apiUrl = "https://entreprise.data.gouv.fr/api/sirene/v1"
    private val searchByFullText = "$apiUrl/full_text/"
    private val numberOfResult = "$searchByFullText"
    private val searchBySiret = "$apiUrl/siret/"



    fun getCompanies(query: String): List<Company>? {
        var conn: HttpsURLConnection? = null
        val datas: MutableList<Company>? = mutableListOf()
        try {
            val url = URL("$searchByFullText${query}?perpage=1")
            println(url)
            conn = url.openConnection() as HttpsURLConnection
            conn.connect()
            val code = conn.responseCode
            if (code != HttpsURLConnection.HTTP_OK) {
                return datas
            }

            val inputStream = conn.inputStream ?: return datas
            val reader = JsonReader(inputStream.bufferedReader())
            reader.beginObject()
            while(reader.hasNext()){

                val nameToRead = reader.nextName()
                if(nameToRead == "etablissement"){
                    reader.beginArray()
                    while (reader.hasNext()) {
                        try {
                            datas?.add(readCompany(reader))
                        } catch (e: IllegalStateException) {
                            return datas
                        }
                    }
                    reader.endArray()
                }else {
                    reader.skipValue();
                }
            }

            if (datas != null) {
                /* add search in db */
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
                val currentDateandTime: String = sdf.format(Date())
                val newSearchHistory = SearchHistory(
                        word = query,
                        date = currentDateandTime
                )
                newSearchHistory.id = searchHistoryDAO.insert(newSearchHistory)

                /* add companies in db */
                for(i in datas){
                    /* Check db to avoid company duplication */
                    if(companyDAO.getBySiret(i.siret) == null){
                        i.id = companyDAO.insert(i)
                    }else{
                        val companyAlreadyExist =  companyDAO.getBySiret(i.siret)
                        i.id = companyAlreadyExist!!.id
                    }

                    /* add SearchHistoryWithCompaniesDAO (joinTable) in db */
                    val newSearchHistoryWithCompaniesDAO = newSearchHistory.id?.let {
                        i.id?.let { it1 ->
                            SearchHistoryWithCompanies(
                                    company = it1,
                                    searchHistory = it
                            )
                        }
                    }
                    if (newSearchHistoryWithCompaniesDAO != null) {
                        searchHistoryWithCompaniesDAO.insert(newSearchHistoryWithCompaniesDAO)
                    }
                }
            }
            return datas
        } catch (e: IOException) {
            println("IOException")
            return datas
        } finally {
            conn?.disconnect()
        }
    }

    private fun readCompany(reader: JsonReader): Company {
        val company = Company(
                null, "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "",
                "", "", "", "",
                "", false, "", "", "", "",
                "", "", "", "", "",
                "", "", "", "", "",
                "", "", "", "", "", false, "",
                "", "", "", "", "", "", "",
                "", "", "", "", "", "", "",
                "", "", "", "",
                "", false, "", "", "",
                "", "", "", "",
                "", "", "", "",
                "", "", "",
                "", "", "",
                "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "",
                "", ""
        )
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            if (reader.peek() == JsonToken.NULL) {
                reader.skipValue()
                continue
            }
            when(name) {
                "siren" -> company.siren = reader.nextString()
                "siret" -> company.siret = reader.nextString()
                "nic" -> company.nic = reader.nextString()

                "l1_normalisee" -> company.l1_normalisee = reader.nextString()
                "l2_normalisee" -> company.l2_normalisee = reader.nextString()
                "l3_normalisee" -> company.l3_normalisee = reader.nextString()
                "l4_normalisee" -> company.l4_normalisee = reader.nextString()
                "l5_normalisee" -> company.l5_normalisee = reader.nextString()
                "l6_normalisee" -> company.l6_normalisee = reader.nextString()
                "l7_normalisee" -> company.l7_normalisee = reader.nextString()

                "l1_declaree" -> company.l1_declaree = reader.nextString()
                "l2_declaree" -> company.l2_declaree = reader.nextString()
                "l3_declaree" -> company.l3_declaree = reader.nextString()
                "l4_declaree" -> company.l4_declaree = reader.nextString()
                "l5_declaree" -> company.l5_declaree = reader.nextString()
                "l6_declaree" -> company.l6_declaree = reader.nextString()
                "l7_declaree" -> company.l7_declaree = reader.nextString()

                "numero_voie" -> company.numero_voie = reader.nextString()
                "indice_repetition" -> company.indice_repetition = reader.nextString()
                "type_voie" -> company.type_voie = reader.nextString()
                "libelle_voie" -> company.libelle_voie = reader.nextString()
                "code_postal" -> company.code_postal = reader.nextString()
                "cedex" -> company.cedex = reader.nextString()
                "region" -> company.region = reader.nextString()
                "libelle_region" -> company.libelle_region = reader.nextString()
                "departement" -> company.departement = reader.nextString()
                "arrondissement" -> company.arrondissement = reader.nextString()
                "canton" -> company.canton = reader.nextString()
                "commune" -> company.commune = reader.nextString()
                "libelle_commune" -> company.libelle_commune = reader.nextString()
                "departement_unite_urbaine" -> company.departement_unite_urbaine =
                        reader.nextString()
                "taille_unite_urbaine" -> company.taille_unite_urbaine = reader.nextString()
                "numero_unite_urbaine" -> company.numero_unite_urbaine = reader.nextString()
                "etablissement_public_cooperation_intercommunale" -> company.etablissement_public_cooperation_intercommunale =
                        reader.nextString()
                "tranche_commune_detaillee" -> company.tranche_commune_detaillee =
                        reader.nextString()

                "zone_emploi" -> company.zone_emploi = reader.nextString()
                "is_siege" -> company.is_siege = reader.nextString().toBoolean()
                "enseigne" -> company.enseigne = reader.nextString()
                "indicateur_champ_publipostage" -> company.indicateur_champ_publipostage =
                        reader.nextString()
                "statut_prospection" -> company.statut_prospection = reader.nextString()
                "date_introduction_base_diffusion" -> company.date_introduction_base_diffusion =
                        reader.nextString()
                "nature_entrepreneur_individuel" -> company.nature_entrepreneur_individuel =
                        reader.nextString()
                "libelle_nature_entrepreneur_individuel" -> company.libelle_nature_entrepreneur_individuel =
                        reader.nextString()
                "activite_principale" -> company.activite_principale = reader.nextString()
                "libelle_activite_principale" -> company.libelle_activite_principale =
                        reader.nextString()
                "date_validite_activite_principale" -> company.date_validite_activite_principale =
                        reader.nextString()
                "tranche_effectif_salarie" -> company.tranche_effectif_salarie = reader.nextString()
                "libelle_tranche_effectif_salarie" -> company.libelle_tranche_effectif_salarie =
                        reader.nextString()
                "tranche_effectif_salarie_centaine_pret" -> company.tranche_effectif_salarie_centaine_pret =
                        reader.nextString()
                "date_validite_effectif_salarie" -> company.date_validite_effectif_salarie =
                        reader.nextString()
                "origine_creation" -> company.origine_creation = reader.nextString()
                "date_creation" -> company.date_creation = reader.nextString()
                "date_debut_activite" -> company.date_debut_activite = reader.nextString()
                "nature_activite" -> company.nature_activite = reader.nextString()
                "lieu_activite" -> company.lieu_activite = reader.nextString()
                "type_magasin" -> company.type_magasin = reader.nextString()
                "is_saisonnier" -> company.is_saisonnier = reader.nextString().toBoolean()
                "modalite_activite_principale" -> company.modalite_activite_principale =
                        reader.nextString()
                "caractere_productif" -> company.caractere_productif = reader.nextString()
                "participation_particuliere_production" -> company.participation_particuliere_production =
                        reader.nextString()
                "caractere_auxiliaire" -> company.caractere_auxiliaire = reader.nextString()
                "nom_raison_sociale" -> company.nom_raison_sociale = reader.nextString()
                "sigle" -> company.sigle = reader.nextString()
                "nom" -> company.nom = reader.nextString()
                "prenom" -> company.prenom = reader.nextString()
                "civilite" -> company.civilite = reader.nextString()
                "numero_rna" -> company.numero_rna = reader.nextString()
                "nic_siege" -> company.nic_siege = reader.nextString()
                "region_siege" -> company.region_siege = reader.nextString()
                "departement_commune_siege" -> company.departement_commune_siege =
                        reader.nextString()
                "email" -> company.email = reader.nextString()

                "nature_juridique_entreprise" -> company.nature_juridique_entreprise =
                        reader.nextString()
                "libelle_nature_juridique_entreprise" -> company.libelle_nature_juridique_entreprise =
                        reader.nextString()
                "activite_principale_entreprise" -> company.activite_principale_entreprise =
                        reader.nextString()
                "libelle_activite_principale_entreprise" -> company.libelle_activite_principale_entreprise =
                        reader.nextString()
                "date_validite_activite_principale_entreprise" -> company.date_validite_activite_principale_entreprise =
                        reader.nextString()
                "activite_principale_registre_metier" -> company.activite_principale_registre_metier =
                        reader.nextString()

                "is_ess" -> company.is_ess = reader.nextString().toBoolean()
                "date_ess" -> company.date_ess = reader.nextString()
                "tranche_effectif_salarie_entreprise" -> company.tranche_effectif_salarie_entreprise =
                        reader.nextString()
                "libelle_tranche_effectif_salarie_entreprise" -> company.libelle_tranche_effectif_salarie_entreprise =
                        reader.nextString()
                "tranche_effectif_salarie_entreprise_centaine_pret" -> company.tranche_effectif_salarie_entreprise_centaine_pret =
                        reader.nextString()
                "date_validite_effectif_salarie_entreprise" -> company.date_validite_effectif_salarie_entreprise =
                        reader.nextString()

                "categorie_entreprise" -> company.categorie_entreprise = reader.nextString()
                "date_creation_entreprise" -> company.date_creation_entreprise = reader.nextString()
                "date_introduction_base_diffusion_entreprise" -> company.date_introduction_base_diffusion_entreprise =
                        reader.nextString()
                "indice_monoactivite_entreprise" -> company.indice_monoactivite_entreprise =
                        reader.nextString()
                "modalite_activite_principale_entreprise" -> company.modalite_activite_principale_entreprise =
                        reader.nextString()
                "caractere_productif_entreprise" -> company.caractere_productif_entreprise =
                        reader.nextString()

                "date_validite_rubrique_niveau_entreprise_esa" -> company.date_validite_rubrique_niveau_entreprise_esa =
                        reader.nextString()
                "tranche_chiffre_affaire_entreprise_esa" -> company.tranche_chiffre_affaire_entreprise_esa =
                        reader.nextString()
                "activite_principale_entreprise_esa" -> company.activite_principale_entreprise_esa =
                        reader.nextString()
                "premiere_activite_secondaire_entreprise_esa" -> company.premiere_activite_secondaire_entreprise_esa =
                        reader.nextString()
                "deuxieme_activite_secondaire_entreprise_esa" -> company.deuxieme_activite_secondaire_entreprise_esa =
                        reader.nextString()
                "troisieme_activite_secondaire_entreprise_esa" -> company.troisieme_activite_secondaire_entreprise_esa =
                        reader.nextString()
                "quatrieme_activite_secondaire_entreprise_esa" -> company.quatrieme_activite_secondaire_entreprise_esa =
                        reader.nextString()

                "nature_mise_a_jour" -> company.nature_mise_a_jour = reader.nextString()
                "indicateur_mise_a_jour_1" -> company.indicateur_mise_a_jour_1 = reader.nextString()
                "indicateur_mise_a_jour_2" -> company.indicateur_mise_a_jour_2 = reader.nextString()
                "indicateur_mise_a_jour_3" -> company.indicateur_mise_a_jour_3 = reader.nextString()
                "date_mise_a_jour" -> company.date_mise_a_jour = reader.nextString()
                "created_at" -> company.created_at = reader.nextString()
                "updated_at" -> company.updated_at = reader.nextString()

                "longitude" -> company.longitude = reader.nextString()
                "latitude" -> company.latitude = reader.nextString()
                "geo_score" -> company.geo_score = reader.nextString()
                "geo_type" -> company.geo_type = reader.nextString()
                "geo_adresse" -> company.geo_adresse = reader.nextString()
                "geo_id" -> company.geo_id = reader.nextString()
                "geo_ligne" -> company.geo_ligne = reader.nextString()
                "geo_l4" -> company.geo_l4 = reader.nextString()
                "geo_l5" -> company.geo_l5 = reader.nextString()
                else ->reader.skipValue()
            }
        }
        reader.endObject()
        return company
    }

    fun getCompany(siret: String): Company? {
        var conn: HttpsURLConnection? = null
        var company: Company
        try {
            val url = URL("$searchBySiret${siret}")
            println("url")
            println(url)
            conn = url.openConnection() as HttpsURLConnection
            conn.connect()
            val code = conn.responseCode
            if (code != HttpsURLConnection.HTTP_OK) {
                return null
            }

            val inputStream = conn.inputStream ?: return null
            val reader = JsonReader(inputStream.bufferedReader())
            reader.beginObject()

            while (reader.hasNext()) {
                val name = reader.nextName()
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue()
                    continue
                }
                if(name == "etablissement") {
                    return try {
                        company = readCompany(reader)
                        company
                    } catch (e: IllegalStateException) {
                        null
                    }
                }
            }
            reader.endObject()
            println("endArray")
            return null
        } catch (e: IOException) {
            println("IOException")
            return null
        } finally {
            conn?.disconnect()
        }
    }

    private fun listsEqual(list1: List<Any>, list2: List<Any>): Boolean {

        if (list1.size != list2.size)
            return false

        val pairList = list1.zip(list2)

        return pairList.all { (elt1, elt2) ->
            elt1 == elt2
        }
    }

}