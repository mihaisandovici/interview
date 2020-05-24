package ibmro.common

interface ModelMapper<in INPUT, out OUTPUT>{
    fun map(input: INPUT):OUTPUT
}