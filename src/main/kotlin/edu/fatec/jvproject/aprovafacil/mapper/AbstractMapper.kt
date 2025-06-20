package edu.fatec.jvproject.aprovafacil.mapper

abstract class AbstractMapper<From, To> : IMapper<From, To> {

    fun mapToList(fromList: List<From>): List<To> {
        return fromList.map(this::to)
    }

    fun mapFromList(toList: List<To>): List<From> {
        return toList.map(this::from)
    }
}