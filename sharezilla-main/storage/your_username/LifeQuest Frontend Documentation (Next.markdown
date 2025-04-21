# LifeQuest Frontend Documentation (Next.js)

## Project Overview

**LifeQuest** is a gamified task management web application that transforms tasks into "quests" to make productivity engaging and fun. Users earn experience points (XP), level up, and unlock achievements by completing quests. The app fosters community through "guilds" for collaborative quests and allows XP donations to charities for real-world impact. This documentation focuses on the frontend, built with **Next.js** and **Tailwind CSS**, using dummy data to simulate API responses. The app is designed for PC browsers with Progressive Web App (PWA) support for offline access and a mobile-like experience.

### Objectives

- Create an interactive, RPG-inspired UI with a vibrant, modern vibe.
- Implement core features: quest creation, completion, XP tracking, and guild previews.
- Use dummy data to mimic backend API responses until the Spring Boot backend is developed.
- Leverage Next.js for SEO, performance, and easy API integration.
- Ensure responsiveness for PC and PWA compatibility.

### Target Audience

Individuals (students, professionals, hobbyists) seeking a motivating, fun way to manage tasks and connect with others.

---

## Tech Stack

- **Next.js**: For server-side rendering, static generation, and routing.
- **Tailwind CSS**: For responsive, utility-first styling.
- **PWA**: Next.js PWA setup (via `next-pwa`) for offline access and installability.
- **Dummy Data**: Hardcoded JSON to simulate API responses.
- **Tools**: Axios (for future API calls), Chart.js (for progress visuals).

---

## User Flow

1. **Landing Page**: User sees a welcome screen with a "Get Started" button and app overview (gamified tasks, guilds, impact).
2. **Sign-Up/Login (Mock)**: A placeholder form for user authentication (no backend yet, use dummy user data).
3. **Dashboard**: Main hub showing:
   - User stats (XP, level, achievements).
   - Active quests (list or grid).
   - Guild preview (e.g., "Fitness Warriors").
   - Option to create a new quest.
4. **Create Quest**: Form to add a quest (title, category, difficulty).
5. **Complete Quest**: Mark quests as done, update XP, and show animated feedback.
6. **Guild Page**: View guild details and collaborative quests (static for now).
7. **PWA**: Install the app for offline access; core features (view quests, mark complete) work offline.

---

## Components and Pages

The frontend is modular, with reusable components and Next.js pages. Below are the key pages and components:

### 1. Pages

- `pages/index.js` **(Landing Page)**:

  - **Purpose**: Welcome screen to attract users.
  - **UI**:
    - Hero section: Bold headline ("Turn Tasks into Epic Quests!"), subtext, and "Get Started" button (links to `/dashboard`).
    - Background: Gradient (indigo to blue) with subtle RPG icons (e.g., sword, shield).
    - Footer: Brief app description and static social links.
  - **Data**: Static content (no API calls).
  - **Props**: None.

- `pages/dashboard.js`:

  - **Purpose**: Main hub for quests and user stats.
  - **UI**:
    - Header: User level (e.g., "Level 3 Adventurer") and XP bar (Chart.js linear progress).
    - Quest Grid: Cards for active quests (title, category, difficulty, "Complete" button).
    - Guild Preview: Small card with guild name and member count.
    - Button: "Create New Quest" (links to `/create-quest`).
  - **Data**: Fetch dummy quests and user data on mount (client-side).
  - **State**:
    - `quests`: Array of quest objects.
    - `xp`: User’s total XP.
    - `level`: Calculated from XP (100 XP per level).
  - **Actions**:
    - Fetch quests and user data.
    - Mark quest as complete (update state, add XP).

- `pages/create-quest.js`:

  - **Purpose**: Form to add a new quest.
  - **UI**:
    - Form fields: Title (text), Category (dropdown: Personal, Fitness, Learning), Difficulty (dropdown: Easy, Medium, Epic).
    - Submit button: "Add Quest" (indigo, hover effect).
    - Styling: Centered card with shadow and rounded corners.
  - **Data**: Append to dummy quests on submit.
  - **State**:
    - `formData`: Object with title, category, difficulty.
  - **Actions**:
    - On submit, add quest to dummy data.
    - Redirect to dashboard.

- `pages/guilds.js`:

  - **Purpose**: Display guild details (static for now).
  - **UI**:
    - Header: Guild name (e.g., "Fitness Warriors").
    - Member list: Dummy names and avatars.
    - Group quests: List of shared quests.
  - **Data**: Fetch dummy guilds.
  - **Props**: None.

### 2. Components

- `components/Sidebar.js`:

  - **Purpose**: Navigation menu for dashboard, guilds, and profile.
  - **UI**:
    - Fixed, vertical sidebar (left side, 250px wide).
    - Logo: "LifeQuest" (bold, white text on indigo background).
    - Links: Dashboard, Guilds, Profile (use `Link` from `next/link` for active styles).
    - User stats: Display dummy user name and XP (e.g., "Hero: Alex | XP: 350").
  - **Props**: None.
  - **State**: None.

- `components/QuestCard.js`:

  - **Purpose**: Display a single quest in the dashboard grid.
  - **UI**:
    - Card: Title, category, difficulty, status (Active/Completed).
    - Button: "Complete" (green, disabled if completed).
  - **Props**:
    - `quest`: Quest object (id, title, category, difficulty, completed, xpEarned).
    - `onComplete`: Callback to mark quest as complete.
  - **State**: None.

- `components/XPBar.js`:

  - **Purpose**: Show user’s XP progress.
  - **UI**: Linear progress bar (Chart.js) with indigo fill.
  - **Props**:
    - `xp`: Current XP.
    - `level`: Current level.
  - **State**: None.

---

## Dummy Data (API Simulation)

Use hardcoded JSON in `lib/data.js` to mimic backend responses. Next.js API routes (`pages/api/*`) can also simulate endpoints for a more realistic setup.

### lib/data.js

```javascript
export const dummyUser = {
  id: 1,
  name: "Alex the Adventurer",
  xp: 350,
  level: Math.floor(350 / 100) + 1 // 100 XP per level
};

export let dummyQuests = [
  { id: 1, title: "Run 5km", category: "Fitness", difficulty: "Medium", completed: false, xpEarned: 0 },
  { id: 2, title: "Read 20 pages", category: "Learning", difficulty: "Easy", completed: true, xpEarned: 50 },
  { id: 3, title: "Volunteer at shelter", category: "Community", difficulty: "Epic", completed: false, xpEarned: 0 }
];

export const dummyGuilds = [
  { id: 1, name: "Fitness Warriors", members: ["Alex", "Sam", "Emma"], quests: ["Group Run 10km"] }
];

export const getQuests = () => dummyQuests;
export const addQuest = (quest) => {
  const newQuest = { id: dummyQuests.length + 1, ...quest, completed: false, xpEarned: 0 };
  dummyQuests.push(newQuest);
  return newQuest;
};
export const completeQuest = (id) => {
  const quest = dummyQuests.find(q => q.id === id);
  if (quest && !quest.completed) {
    quest.completed = true;
    quest.xpEarned = getXpForDifficulty(quest.difficulty);
    dummyUser.xp += quest.xpEarned;
    dummyUser.level = Math.floor(dummyUser.xp / 100) + 1;
  }
  return quest;
};
export const getUser = () => dummyUser;
export const getGuilds = () => dummyGuilds;

const getXpForDifficulty = (difficulty) => {
  switch (difficulty) {
    case "Easy": return 50;
    case "Medium": return 100;
    case "Epic": return 200;
    default: return 0;
  }
};
```

### Simulated API Routes (Optional)

Create `pages/api/quests.js` to simulate backend endpoints:

```javascript
export default function handler(req, res) {
  const { getQuests, addQuest, completeQuest } = require('../../lib/data');
  if (req.method === 'GET') {
    res.status(200).json(getQuests());
  } else if (req.method === 'POST') {
    const quest = addQuest(req.body);
    res.status(201).json(quest);
  } else if (req.method === 'PUT') {
    const { id } = req.query;
    const quest = completeQuest(parseInt(id));
    res.status(200).json(quest);
  }
}
```

Use similar routes for `/api/user` and `/api/guilds` if needed.

---

## Styling Guidelines (Vibe Code)

Create a vibrant, RPG-inspired aesthetic that feels adventurous and modern:

- **Colors**:
  - Primary: Indigo (#4f46e5) for buttons, headers, and accents.
  - Secondary: Blue (#3b82f6) for gradients and highlights.
  - Background: Light gray (#f3f4f6) for main content, white (#ffffff) for cards.
  - Success: Green (#22c55e) for completion buttons.
- **Typography**:
  - Font: Google Fonts’ "Poppins" (bold for headings, regular for body).
  - Sizes: 2xl (24px) for headings, base (16px) for text.
- **Components**:
  - Cards: White background, subtle shadow, rounded corners (8px).
  - Buttons: Indigo background, white text, hover: darken by 10%, rounded (6px).
  - Progress Bar: Use Chart.js with indigo fill and animated transitions.
- **Animations**:
  - XP gain: Fade-in number increase (CSS keyframes).
  - Quest completion: Scale-up effect on card (transform: scale(1.05)).
- **Icons**: Use Heroicons or Font Awesome for RPG-themed icons (e.g., sword, shield).

---

## PWA Setup

Use `next-pwa` for easy PWA integration:

1. Install: `npm install next-pwa`.
2. Create `next.config.js`:

```javascript
const withPWA = require('next-pwa')({
  dest: 'public',
  register: true,
  skipWaiting: true
});

module.exports = withPWA({
  // Other Next.js config
});
```

3. Create `public/manifest.json`:

```json
{
  "name": "LifeQuest",
  "short_name": "LifeQuest",
  "start_url": "/",
  "display": "standalone",
  "background_color": "#f3f4f6",
  "theme_color": "#4f46e5",
  "icons": [
    {
      "src": "/icon.png",
      "sizes": "192x192",
      "type": "image/png"
    }
  ]
}
```

4. Add `public/icon.png` (192x192 PNG).

---

## File Structure

```
lifequest/
├── public/
│   ├── icon.png
│   ├── manifest.json
├── pages/
│   ├── api/
│   │   ├── quests.js
│   ├── index.js
│   ├── dashboard.js
│   ├── create-quest.js
│   ├── guilds.js
├── components/
│   ├── Sidebar.js
│   ├── QuestCard.js
│   ├── XPBar.js
├── lib/
│   ├── data.js
├── styles/
│   ├── globals.css
├── next.config.js
├── package.json
```

---

## Development Steps

1. **Setup Project**:

   - Run `npx create-next-app@latest lifequest`.
   - Install dependencies: `npm install tailwindcss@latest postcss@latest autoprefixer@latest next-pwa axios chart.js react-chartjs-2`.
   - Initialize Tailwind: `npx tailwindcss init -p`.

2. **Configure Tailwind**:

   - Update `styles/globals.css`:

     ```css
     @tailwind base;
     @tailwind components;
     @tailwind utilities;
     @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap');
     body { font-family: 'Poppins', sans-serif; }
     ```

   - Update `tailwind.config.js`:

     ```javascript
     module.exports = {
       content: ['./pages/**/*.{js,jsx}', './components/**/*.{js,jsx}'],
       theme: { extend: {} },
       plugins: []
     };
     ```

3. **Create Pages and Components**:

   - Implement pages (`index.js`, `dashboard.js`, etc.) and components as described.
   - Use dummy data from `lib/data.js` or API routes.

4. **Style UI**:

   - Apply Tailwind classes per styling guidelines.
   - Add animations for XP and quest completion.

5. **Setup PWA**:

   - Configure `next-pwa` and add `manifest.json`.
   - Test installability and offline mode.

6. **Test**:

   - Verify SSR/SSG on PC (Chrome, Firefox).
   - Test PWA: Install app, check offline mode (view quests, mark complete).
   - Ensure responsiveness for smaller screens.

---

## Sample Code

### pages/dashboard.js

```javascript
import { useState, useEffect } from 'react';
import Link from 'next/link';
import Sidebar from '../components/Sidebar';
import QuestCard from '../components/QuestCard';
import XPBar from '../components/XPBar';
import { getQuests, completeQuest, getUser } from '../lib/data';

export default function Dashboard() {
  const [quests, setQuests] = useState([]);
  const [user, setUser] = useState({ xp: 0, level: 1 });

  useEffect(() => {
    setQuests(getQuests());
    setUser(getUser());
  }, []);

  const handleComplete = (id) => {
    const updatedQuest = completeQuest(id);
    setQuests(quests.map(q => (q.id === id ? updatedQuest : q)));
    setUser(getUser());
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      <Sidebar />
      <main className="flex-1 p-6">
        <h1 className="text-2xl font-bold mb-4">Level {user.level} Adventurer</h1>
        <XPBar xp={user.xp} level={user.level} />
        <Link href="/create-quest">
          <button className="bg-indigo-500 text-white px-4 py-2 rounded hover:bg-indigo-600 mb-6">
            Create New Quest
          </button>
        </Link>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {quests.map(quest => (
            <QuestCard key={quest.id} quest={quest} onComplete={handleComplete} />
          ))}
        </div>
      </main>
    </div>
  );
}
```

### components/QuestCard.js

```javascript
export default function QuestCard({ quest, onComplete }) {
  return (
    <div className="bg-white p-4 rounded-lg shadow transform hover:scale-105 transition-transform">
      <h3 className="text-lg font-semibold">{quest.title}</h3>
      <p>{quest.category} - {quest.difficulty}</p>
      <p>{quest.completed ? 'Completed' : 'Active'}</p>
      {!quest.completed && (
        <button
          onClick={() => onComplete(quest.id)}
          className="bg-green-500 text-white px-4 py-2 mt-2 rounded hover:bg-green-600"
        >
          Complete
        </button>
      )}
    </div>
  );
}
```

### components/XPBar.js

```javascript
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, LineElement, CategoryScale, LinearScale, PointElement } from 'chart.js';

ChartJS.register(LineElement, CategoryScale, LinearScale, PointElement);

export default function XPBar({ xp, level }) {
  const data = {
    labels: ['Progress'],
    datasets: [
      {
        label: 'XP',
        data: [xp % 100],
        backgroundColor: '#4f46e5',
        borderColor: '#4f46e5',
        fill: true
      }
    ]
  };

  const options = {
    scales: { y: { max: 100, min: 0 } },
    plugins: { legend: { display: false } }
  };

  return (
    <div className="mb-6">
      <p>XP: {xp} / {level * 100}</p>
      <Line data={data} options={options} />
    </div>
  );
}
```

---

## Future Backend Integration

When ready to develop the Spring Boot backend:

- Replace `lib/data.js` with Axios calls to `/api/quests`, `/api/user`, `/api/guilds` (e.g., `http://localhost:8080/api/quests`).
- Implement JWT authentication for user sessions.
- Add WebSocket for real-time guild chat.
- Integrate Google Maps API for location-based quests or Stripe for donations.

---

## Success Criteria

- UI is vibrant, RPG-inspired, and responsive on PC.
- Quests can be created, viewed, and completed with XP updates.
- Sidebar navigation works smoothly across pages.
- PWA is installable and supports offline quest viewing.
- Code is modular, well-documented, and optimized for Next.js (SSR/SSG).
- Ready for backend integration with minimal changes.