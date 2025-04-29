import { TransactionList } from "./pages/TransactionList";
import { Toaster } from "./components/ui/toaster";

import "./App.css";
import tenpoLogo from "./assets/tenpo.png";

function App() {
  return (
    <main className="min-h-screen p-4 md:p-8">
      <div className="container mx-auto">
        <div className="flex items-center mb-6">
          <img src={tenpoLogo} alt="Tenpo Logo" className="w-40 h-20 mr-3" />
          <h1 className="text-3xl font-bold">Panel de Transacciones</h1>
        </div>
        <TransactionList />
        <Toaster />
      </div>
    </main>
  );
}

export default App;
