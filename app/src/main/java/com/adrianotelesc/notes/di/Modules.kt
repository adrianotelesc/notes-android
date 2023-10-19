package com.adrianotelesc.notes.di

import com.adrianotelesc.notes.data.repository.NoteRepository
import com.adrianotelesc.notes.data.repository.NoteRepositoryImpl
import com.adrianotelesc.notes.ui.screen.noteediting.NoteEditingViewModel
import com.adrianotelesc.notes.ui.screen.notes.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule: Module
    get() = module {
        includes(
            viewModelModule,
            repoModule,
        )
    }

private val viewModelModule = module {
    viewModelOf(::NotesViewModel)
    viewModel { params ->
        NoteEditingViewModel(
            noteId = params[0],
            noteRepo = get()
        )
    }
}

private val repoModule = module {
    singleOf<NoteRepository>(::NoteRepositoryImpl)
}
