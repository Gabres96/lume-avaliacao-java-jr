import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Customers from "./pages/Customers";
import PrivateRoute from "./components/PrivateRoute";

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route
              path="/customers"
              element={
                <PrivateRoute>
                  <Customers />
                </PrivateRoute>
              }
          />
        </Routes>
      </BrowserRouter>
  );
}

export default App;
