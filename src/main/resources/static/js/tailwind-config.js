tailwind.config = {
    darkMode: "class",
    theme: {
        extend: {
            colors: {
                "primary": "#137fec",
                "primary-dark": "#0e5fb0",
                "primary-hover": "#0e6ac7",
                "background-light": "#f6f7f8",
                "background-dark": "#101922",
                "surface-light": "#ffffff",
                "surface-dark": "#1a2632",
                "text-main": "#0d141b",
                "text-sub": "#4c739a",
            },
            fontFamily: {
                "display": ["Lexend", "Noto Sans SC", "sans-serif"],
                "sans": ["Lexend", "Noto Sans SC", "sans-serif"],
            },
            borderRadius: {
                "DEFAULT": "0.25rem",
                "lg": "0.5rem",
                "xl": "0.75rem",
                "full": "9999px"
            },
        },
    },
}