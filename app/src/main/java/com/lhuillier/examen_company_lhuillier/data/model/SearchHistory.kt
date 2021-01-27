package com.lhuillier.examen_company_lhuillier.data.model

import androidx.room.*


@Entity(
        indices = [ Index(value = ["id"], unique = true) ]
)
data class SearchHistory(@PrimaryKey(autoGenerate = true)
                         var id: Long? = null,
                         var word: String,
                         var results: List<Company>? = null,
                         var date: String
            ){

    override fun toString(): String {
        return "$word ($date)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchHistory

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode() ?: 0
    }

}
