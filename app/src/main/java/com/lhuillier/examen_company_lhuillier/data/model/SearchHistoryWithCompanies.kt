package com.lhuillier.examen_company_lhuillier.data.model

import androidx.room.*

@Entity(
        indices = [ Index(value = ["id"], unique = true) ],
        foreignKeys = [
                ForeignKey(
                        entity = SearchHistory::class,
                        parentColumns = ["id"],
                        childColumns = ["searchHistory"],
                        onDelete = ForeignKey.CASCADE
                ),
                ForeignKey(
                        entity = Company::class,
                        parentColumns = ["id"],
                        childColumns = ["company"],
                        onDelete = ForeignKey.CASCADE
                )]
)
data class SearchHistoryWithCompanies(@PrimaryKey(autoGenerate = true)
                var id:Long? = null,
                var company: Long,
                var searchHistory: Long) {

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as SearchHistoryWithCompanies

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id.hashCode() ?: 0
        }

}
