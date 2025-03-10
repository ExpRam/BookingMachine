import {FC, HTMLAttributes, PropsWithChildren} from 'react';
import * as React from "react";

interface ModalProps extends PropsWithChildren, HTMLAttributes<HTMLDivElement> {
    visible?: boolean;
    closeModal: () => void;
}

const Modal: FC<ModalProps> = ({visible, closeModal, children, ...props}) => {
    let showOrHide = "hidden";

    if(visible) {
        showOrHide = "flex justify-center items-center";
    }

    return (
        <div {...props} className={`fixed overflow-y-auto absolute top-0 left-0 bottom-0 right-0 bg-black/50 ${showOrHide}`} onClick={() => closeModal()}>
            <div className="m-auto p-10 bg-white rounded-lg min-w-[250px]" onClick={(e: React.MouseEvent) => e.stopPropagation()}>
                {children}
            </div>
        </div>
    );
};

export default Modal;