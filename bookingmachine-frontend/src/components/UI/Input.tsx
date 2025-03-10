import {FC, InputHTMLAttributes} from 'react';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
    className?: string;
}

const Input: FC<InputProps> = ({className, ...props}) => {
    return (
        <input
            {...props}
            className={`w-full border border-gray-300 p-2 rounded-xl focus:ring-blue-500 ${className}`}
        />
    );
};

export default Input;