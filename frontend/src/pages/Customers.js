import { useEffect, useState } from "react";
import api from "../services/api";
import { logout } from "../services/authService";

export default function Customers() {
    const [customers, setCustomers] = useState([]);
    const [name, setName] = useState("");
    const [cpf, setCpf] = useState("");
    const [cep, setCep] = useState("");
    const [editingCustomer, setEditingCustomer] = useState(null);

    const loadCustomers = async () => {
        const response = await api.get("/api/customers");
        setCustomers(response.data);
    };

    useEffect(() => {
        loadCustomers();
    }, []);

    const handleSubmit = async () => {
        if (editingCustomer) {
            // UPDATE
            await api.put(`/api/customers/${editingCustomer.id}`, {
                name,
                cpf,
                cep,
            });
        } else {
            // CREATE
            await api.post("/api/customers", {
                name,
                cpf,
                cep,
            });
        }

        clearForm();
        loadCustomers();
    };

    const deleteCustomer = async (id) => {
        await api.delete(`/api/customers/${id}`);
        loadCustomers();
    };

    const editCustomer = (customer) => {
        setEditingCustomer(customer);
        setName(customer.name);
        setCpf(customer.cpf);
        setCep(customer.cep);
    };

    const clearForm = () => {
        setName("");
        setCpf("");
        setCep("");
        setEditingCustomer(null);
    };

    const handleLogout = async () => {
        try {
            await logout();
        } finally {
            localStorage.clear();
            window.location.href = "/";
        }
    };

    return (
        <div>
            <button onClick={handleLogout}>Sair</button>

            <h2>Clientes</h2>

            <input
                placeholder="Nome"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />

            <input
                placeholder="CPF"
                value={cpf}
                onChange={(e) => setCpf(e.target.value)}
            />

            <input
                placeholder="CEP"
                value={cep}
                onChange={(e) => setCep(e.target.value)}
            />

            <button onClick={handleSubmit}>
                {editingCustomer ? "Atualizar" : "Cadastrar"}
            </button>

            {editingCustomer && (
                <button onClick={clearForm}>Cancelar</button>
            )}

            <ul>
                {customers.map((c) => (
                    <li key={c.id}>
                        <strong>{c.name}</strong><br />
                        CPF: {c.cpf}<br />
                        CEP: {c.cep}<br />
                        Endereço: {c.logradouro}, {c.bairro}<br />
                        {c.cidade} - {c.estado}
                        <br /><br />

                        <button onClick={() => editCustomer(c)}>Editar</button>
                        <button onClick={() => deleteCustomer(c.id)}>Excluir</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
