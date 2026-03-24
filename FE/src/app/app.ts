import { Component, computed, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ThemeService } from './services/theme.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  private readonly themeService = inject(ThemeService);

  protected readonly isDarkMode = computed(() => this.themeService.isDarkMode());

  protected toggleTheme(): void {
    this.themeService.toggleTheme();
  }
}
