import RefundForm from '../components/forms/RefundForm.tsx';
import RefundAlert from '../components/RefundAlert.tsx';
import { useRefund } from '../hooks/UseRefund.ts';

const RefundPage = () => {
    const { showAlert, result, proceedResult, onClose } = useRefund();

    return (
        <main>
            <RefundForm callback={proceedResult} />
            {showAlert && <RefundAlert success={result} onClose={onClose} />}
        </main>
    );
};

export default RefundPage;
