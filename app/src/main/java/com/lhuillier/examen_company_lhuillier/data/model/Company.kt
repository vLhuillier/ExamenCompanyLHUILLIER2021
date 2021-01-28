package com.lhuillier.examen_company_lhuillier.data.model

import androidx.room.*
import java.io.Serializable

@Entity(
        indices = [ Index(value = ["id"], unique = true) ]
)
data class Company (@PrimaryKey(autoGenerate = true)
                   var id                                                 : Long? = null,
                   var siren                                              : String,
                   var siret                                              : String,
                   var nic                                                : String,

                   var l1_normalisee                                      : String,
                   var l2_normalisee                                      : String,
                   var l3_normalisee                                      : String,
                   var l4_normalisee                                      : String,
                   var l5_normalisee                                      : String,
                   var l6_normalisee                                      : String,
                   var l7_normalisee                                      : String,
                   var l1_declaree                                        : String,
                   var l2_declaree                                        : String,
                   var l3_declaree                                        : String,
                   var l4_declaree                                        : String,
                   var l5_declaree                                        : String,
                   var l6_declaree                                        : String,
                   var l7_declaree                                        : String,
                   var numero_voie                                        : String,
                   var indice_repetition                                  : String,
                   var type_voie                                          : String,
                   var libelle_voie                                       : String,
                   var code_postal                                        : String,
                   var cedex                                              : String,
                   var region                                             : String,
                   var libelle_region                                     : String,
                   var departement                                        : String,

                   var arrondissement                                     : String,
                   var canton                                             : String,
                   var commune                                            : String,
                   var libelle_commune                                    : String,
                   var departement_unite_urbaine                          : String,
                   var taille_unite_urbaine                               : String,
                   var numero_unite_urbaine                               : String,
                   var etablissement_public_cooperation_intercommunale    : String,
                   var tranche_commune_detaillee                          : String,
                   var zone_emploi                                        : String,
                   var is_siege                                           : Boolean,
                   var enseigne                                           : String,
                   var indicateur_champ_publipostage                      : String,

                   var statut_prospection                                 : String,
                   var date_introduction_base_diffusion                   : String,
                   var nature_entrepreneur_individuel                     : String,
                   var libelle_nature_entrepreneur_individuel             : String,
                   var activite_principale                                : String,
                   var libelle_activite_principale                        : String,
                   var date_validite_activite_principale                  : String,

                   var tranche_effectif_salarie                           : String,
                   var libelle_tranche_effectif_salarie                   : String,
                   var tranche_effectif_salarie_centaine_pret             : String,
                   var date_validite_effectif_salarie                     : String,
                   var origine_creation                                   : String,
                   var date_creation                                      : String,
                   var date_debut_activite                                : String,
                   var nature_activite                                    : String,
                   var lieu_activite                                      : String,

                   var type_magasin                                       : String,
                   var is_saisonnier                                      : Boolean,
                   var modalite_activite_principale                       : String,
                   var caractere_productif                                : String,
                   var participation_particuliere_production              : String,
                   var caractere_auxiliaire                               : String,

                   var nom_raison_sociale                                 : String,
                   var sigle                                              : String,
                   var nom                                                : String,
                   var prenom                                             : String,
                   var civilite                                           : String,
                   var numero_rna                                         : String,
                   var nic_siege                                          : String,
                   var region_siege                                       : String,

                   var departement_commune_siege                          : String,
                   var email                                              : String,
                   var nature_juridique_entreprise                        : String,
                   var libelle_nature_juridique_entreprise                : String,
                   var activite_principale_entreprise                     : String,
                   var libelle_activite_principale_entreprise             : String,
                   var date_validite_activite_principale_entreprise       : String,
                   var activite_principale_registre_metier                : String,
                   var is_ess                                             : Boolean,

                   var date_ess                                           : String,
                   var tranche_effectif_salarie_entreprise                : String,
                   var libelle_tranche_effectif_salarie_entreprise        : String,
                   var tranche_effectif_salarie_entreprise_centaine_pret  : String,
                   var date_validite_effectif_salarie_entreprise          : String,
                   var categorie_entreprise                               : String,

                   var date_creation_entreprise                           : String,
                   var date_introduction_base_diffusion_entreprise        : String,
                   var indice_monoactivite_entreprise                     : String,
                   var modalite_activite_principale_entreprise            : String,

                   var caractere_productif_entreprise                     : String,
                   var date_validite_rubrique_niveau_entreprise_esa       : String,
                   var tranche_chiffre_affaire_entreprise_esa             : String,
                   var activite_principale_entreprise_esa                 : String,
                   var premiere_activite_secondaire_entreprise_esa        : String,
                   var deuxieme_activite_secondaire_entreprise_esa        : String,
                   var troisieme_activite_secondaire_entreprise_esa       : String,
                   var quatrieme_activite_secondaire_entreprise_esa       : String,

                   var nature_mise_a_jour                                 : String,
                   var indicateur_mise_a_jour_1                           : String,
                   var indicateur_mise_a_jour_2                           : String,
                   var indicateur_mise_a_jour_3                           : String,
                   var date_mise_a_jour                                   : String,
                   var created_at                                         : String,
                   var updated_at                                         : String,
                   var longitude                                          : String,
                   var latitude                                           : String,

                   var geo_score                                          : String,
                   var geo_type                                           : String,
                   var geo_adresse                                        : String,
                   var geo_id                                             : String,
                   var geo_ligne                                          : String,
                   var geo_l4                                             : String,
                   var geo_l5                                             : String
                   ): Serializable {

    override fun toString(): String {
        if(departement.isNullOrEmpty()){
            departement = "ND"
        }
        return String.format("%s (%s)", l1_normalisee, departement )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Company

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode() ?: 0
    }

}
