import { createRoot } from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import './styles/tailwind.css';
import { BOOKING_PAGE, HOME_PAGE, NOT_FOUND_PAGE, REFUND_PAGE } from './utils/consts.ts';

import MainLayout from './layouts/MainLayout.tsx';
import IndexPage from './pages/IndexPage.tsx';
import BookingPage from './pages/BookingPage.tsx';
import RefundPage from './pages/RefundPage.tsx';
import NotFound from './pages/NotFound.tsx';

createRoot(document.getElementById('root')!).render(
    <BrowserRouter>
        <Routes>
            <Route path={HOME_PAGE} element={<MainLayout />}>
                <Route index element={<IndexPage />} />
                <Route path={REFUND_PAGE} element={<RefundPage />} />
                <Route path={BOOKING_PAGE} element={<BookingPage />} />
            </Route>
            <Route path={NOT_FOUND_PAGE} element={<NotFound />} />
        </Routes>
    </BrowserRouter>
);
