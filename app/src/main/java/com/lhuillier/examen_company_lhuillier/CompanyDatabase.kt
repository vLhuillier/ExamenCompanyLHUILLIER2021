package com.lhuillier.examen_company_lhuillier

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lhuillier.examen_company_lhuillier.data.model.SearchHistory
import com.lhuillier.examen_company_lhuillier.data.tier.SearchHistoryDAO
import com.lhuillier.examen_company_lhuillier.data.model.Company
import com.lhuillier.examen_company_lhuillier.data.tier.CompanyDAO
import com.lhuillier.examen_company_lhuillier.data.tier.DataConverter
import com.lhuillier.examen_company_lhuillier.data.tier.DateConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@Database(version = 3, entities = [Company::class, SearchHistory::class])
@TypeConverters(*[DataConverter::class, DateConverter::class])

abstract class CompanyDatabase: RoomDatabase() {

    abstract fun companyDao(): CompanyDAO;
    abstract fun searchHistoryDao(): SearchHistoryDAO;

    fun seed() {
        try {
            if (companyDao().count() == 0 ) {

                 val c1: Company = Company(
                                siren = "test",
                                siret = "test",
                                nic = "test",
                                l1_normalisee = "test",
                                l2_normalisee = "test",
                                l3_normalisee = "test",
                                l4_normalisee = "test",
                                l5_normalisee = "test",
                                l6_normalisee = "test",
                                l7_normalisee = "test",
                                l1_declaree = "test",
                                l2_declaree = "test",
                                l3_declaree = "test",
                                l4_declaree = "test",
                                l5_declaree = "test",
                                l6_declaree = "test",
                                l7_declaree = "test",
                                numero_voie = "test",
                                indice_repetition = "test",
                                type_voie = "test",
                                libelle_voie = "test",
                                code_postal = "test",
                                cedex = "test",
                                region = "test",
                                libelle_region = "test",
                                departement = "test",
                                arrondissement = "test",
                                canton = "test",
                                commune = "test",
                                libelle_commune = "test",
                                departement_unite_urbaine = "test",
                                taille_unite_urbaine = "test",
                                numero_unite_urbaine = "test",
                                etablissement_public_cooperation_intercommunale = "test",
                                tranche_commune_detaillee = "test",
                                zone_emploi = "test",
                                is_siege = true,
                                enseigne = "test",
                                indicateur_champ_publipostage = "test",
                                statut_prospection = "test",
                                date_introduction_base_diffusion = "test",
                                nature_entrepreneur_individuel = "test",
                                libelle_nature_entrepreneur_individuel = "test",
                                activite_principale = "test",
                                libelle_activite_principale = "test",
                                date_validite_activite_principale = "test",
                                tranche_effectif_salarie = "test",
                                libelle_tranche_effectif_salarie = "test",
                                tranche_effectif_salarie_centaine_pret = "test",
                                date_validite_effectif_salarie = "test",
                                origine_creation = "test",
                                date_creation = "test",
                                date_debut_activite = "test",
                                nature_activite = "test",
                                lieu_activite = "test",
                                type_magasin = "test",
                                is_saisonnier = true,
                                modalite_activite_principale = "test",
                                caractere_productif = "test",
                                participation_particuliere_production = "test",
                                caractere_auxiliaire = "test",
                                nom_raison_sociale = "test",
                                sigle = "test",
                                nom = "test",
                                prenom = "test",
                                civilite = "test",
                                numero_rna = "test",
                                nic_siege = "test",
                                region_siege = "test",
                                departement_commune_siege = "test",
                                email = "test",
                                nature_juridique_entreprise = "test",
                                libelle_nature_juridique_entreprise = "test",
                                activite_principale_entreprise = "test",
                                libelle_activite_principale_entreprise = "test",
                                date_validite_activite_principale_entreprise = "test",
                                activite_principale_registre_metier = "test",
                                is_ess = true,
                                date_ess = "test",
                                tranche_effectif_salarie_entreprise = "test",
                                libelle_tranche_effectif_salarie_entreprise = "test",
                                tranche_effectif_salarie_entreprise_centaine_pret = "test",
                                date_validite_effectif_salarie_entreprise = "test",
                                categorie_entreprise = "test",
                                date_creation_entreprise = "test",
                                date_introduction_base_diffusion_entreprise = "test",
                                indice_monoactivite_entreprise = "test",
                                modalite_activite_principale_entreprise = "test",
                                caractere_productif_entreprise = "test",
                                date_validite_rubrique_niveau_entreprise_esa = "test",
                                tranche_chiffre_affaire_entreprise_esa = "test",
                                activite_principale_entreprise_esa = "test",
                                premiere_activite_secondaire_entreprise_esa = "test",
                                deuxieme_activite_secondaire_entreprise_esa = "test",
                                troisieme_activite_secondaire_entreprise_esa = "test",
                                quatrieme_activite_secondaire_entreprise_esa = "test",
                                nature_mise_a_jour = "test",
                                indicateur_mise_a_jour_1 = "test",
                                indicateur_mise_a_jour_2 = "test",
                                indicateur_mise_a_jour_3 = "test",
                                date_mise_a_jour = "test",
                                created_at = "test",
                                updated_at = "test",
                                longitude = "test",
                                latitude = "test",
                                geo_score = "test",
                                geo_type = "test",
                                geo_adresse = "test",
                                geo_id = "test",
                                geo_ligne = "test",
                                geo_l4 = "test",
                                geo_l5 = "test"
                        )
                companyDao().insert(c1)

                val c2: Company = Company(
                        siren = "test2",
                        siret = "test2",
                        nic = "test2",
                        l1_normalisee = "test2",
                        l2_normalisee = "test2",
                        l3_normalisee = "test2",
                        l4_normalisee = "test2",
                        l5_normalisee = "test2",
                        l6_normalisee = "test2",
                        l7_normalisee = "test2",
                        l1_declaree = "test2",
                        l2_declaree = "test2",
                        l3_declaree = "test2",
                        l4_declaree = "test2",
                        l5_declaree = "test2",
                        l6_declaree = "test2",
                        l7_declaree = "test2",
                        numero_voie = "test2",
                        indice_repetition = "test2",
                        type_voie = "test2",
                        libelle_voie = "test2",
                        code_postal = "test2",
                        cedex = "test2",
                        region = "test2",
                        libelle_region = "test2",
                        departement = "test2",
                        arrondissement = "test2",
                        canton = "test2",
                        commune = "test2",
                        libelle_commune = "test2",
                        departement_unite_urbaine = "test2",
                        taille_unite_urbaine = "test2",
                        numero_unite_urbaine = "test2",
                        etablissement_public_cooperation_intercommunale = "test2",
                        tranche_commune_detaillee = "test2",
                        zone_emploi = "test2",
                        is_siege = true,
                        enseigne = "test2",
                        indicateur_champ_publipostage = "test2",
                        statut_prospection = "test2",
                        date_introduction_base_diffusion = "test2",
                        nature_entrepreneur_individuel = "test2",
                        libelle_nature_entrepreneur_individuel = "test2",
                        activite_principale = "test2",
                        libelle_activite_principale = "test2",
                        date_validite_activite_principale = "test2",
                        tranche_effectif_salarie = "test2",
                        libelle_tranche_effectif_salarie = "test2",
                        tranche_effectif_salarie_centaine_pret = "test2",
                        date_validite_effectif_salarie = "test2",
                        origine_creation = "test2",
                        date_creation = "test2",
                        date_debut_activite = "test2",
                        nature_activite = "test2",
                        lieu_activite = "test2",
                        type_magasin = "test2",
                        is_saisonnier = true,
                        modalite_activite_principale = "test2",
                        caractere_productif = "test2",
                        participation_particuliere_production = "test2",
                        caractere_auxiliaire = "test2",
                        nom_raison_sociale = "test2",
                        sigle = "test2",
                        nom = "test2",
                        prenom = "test2",
                        civilite = "test2",
                        numero_rna = "test2",
                        nic_siege = "test2",
                        region_siege = "test2",
                        departement_commune_siege = "test2",
                        email = "test2",
                        nature_juridique_entreprise = "test2",
                        libelle_nature_juridique_entreprise = "test2",
                        activite_principale_entreprise = "test2",
                        libelle_activite_principale_entreprise = "test2",
                        date_validite_activite_principale_entreprise = "test2",
                        activite_principale_registre_metier = "test2",
                        is_ess = true,
                        date_ess = "test2",
                        tranche_effectif_salarie_entreprise = "test2",
                        libelle_tranche_effectif_salarie_entreprise = "test2",
                        tranche_effectif_salarie_entreprise_centaine_pret = "test2",
                        date_validite_effectif_salarie_entreprise = "test2",
                        categorie_entreprise = "test2",
                        date_creation_entreprise = "test2",
                        date_introduction_base_diffusion_entreprise = "test2",
                        indice_monoactivite_entreprise = "test2",
                        modalite_activite_principale_entreprise = "test2",
                        caractere_productif_entreprise = "test2",
                        date_validite_rubrique_niveau_entreprise_esa = "test2",
                        tranche_chiffre_affaire_entreprise_esa = "test2",
                        activite_principale_entreprise_esa = "test2",
                        premiere_activite_secondaire_entreprise_esa = "test2",
                        deuxieme_activite_secondaire_entreprise_esa = "test2",
                        troisieme_activite_secondaire_entreprise_esa = "test2",
                        quatrieme_activite_secondaire_entreprise_esa = "test2",
                        nature_mise_a_jour = "test2",
                        indicateur_mise_a_jour_1 = "test2",
                        indicateur_mise_a_jour_2 = "test2",
                        indicateur_mise_a_jour_3 = "test2",
                        date_mise_a_jour = "test2",
                        created_at = "test2",
                        updated_at = "test2",
                        longitude = "test2",
                        latitude = "test2",
                        geo_score = "test2",
                        geo_type = "test2",
                        geo_adresse = "test2",
                        geo_id = "test2",
                        geo_ligne = "test2",
                        geo_l4 = "test2",
                        geo_l5 = "test2"
                )
                companyDao().insert(c2)

            }
        } catch (pe: ParseException) {
        }
    }

    companion object {
        private var INSTANCE: CompanyDatabase? = null
        @JvmStatic fun getDatabase(context: Context): CompanyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                        .databaseBuilder(context, CompanyDatabase::class.java, "company.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE!!
        }
    }
}