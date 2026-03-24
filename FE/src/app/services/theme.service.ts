import { Injectable, signal } from '@angular/core';

type ThemeMode = 'light' | 'dark';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private readonly storageKey = 'theme';
  private readonly primeThemeLinkId = 'primeng-theme';

  readonly mode = signal<ThemeMode>('light');

  initialize(): void {
    const initialMode = this.getInitialMode();
    this.applyTheme(initialMode);
  }

  toggleTheme(): void {
    this.setTheme(this.mode() === 'dark' ? 'light' : 'dark');
  }

  setTheme(mode: ThemeMode): void {
    this.applyTheme(mode);
  }

  isDarkMode(): boolean {
    return this.mode() === 'dark';
  }

  private getInitialMode(): ThemeMode {
    const stored = localStorage.getItem(this.storageKey);
    if (stored === 'dark' || stored === 'light') {
      return stored;
    }

    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }

  private applyTheme(mode: ThemeMode): void {
    this.mode.set(mode);
    localStorage.setItem(this.storageKey, mode);

    const isDark = mode === 'dark';
    const html = document.documentElement;

    // Tailwind uses the `dark` class on <html> as a source of truth.
    html.classList.toggle('dark', isDark);
    html.dataset['theme'] = mode;

    // PrimeNG swaps theme by updating the stylesheet href.
    this.syncPrimeNgThemeLink(mode);
  }

  private syncPrimeNgThemeLink(mode: ThemeMode): void {
    const themeLink = document.getElementById(this.primeThemeLinkId) as HTMLLinkElement | null;
    if (!themeLink) {
      return;
    }

    const lightThemeHref = themeLink.dataset['lightTheme'];
    const darkThemeHref = themeLink.dataset['darkTheme'];

    if (!lightThemeHref || !darkThemeHref) {
      return;
    }

    themeLink.href = mode === 'dark' ? darkThemeHref : lightThemeHref;
  }
}
