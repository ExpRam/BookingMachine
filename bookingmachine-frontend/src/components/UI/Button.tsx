import {ButtonHTMLAttributes, FC, PropsWithChildren} from 'react';

interface ButtonProps extends PropsWithChildren, ButtonHTMLAttributes<HTMLButtonElement> {
    className?: string;
}

const Button: FC<ButtonProps> = ({className, children, ...props}) => {
    const baseStyles = "bg-blue-600 font-bold text-white rounded-2xl hover:cursor-pointer hover:bg-blue-700 transition";

    return (
        <button {...props} className={`${baseStyles} ${className}`}>
            {children}
        </button>
    );
};

export default Button;