package com.adrianotelesc.postnote.di

import com.adrianotelesc.postnote.data.repository.NoteRepository
import com.adrianotelesc.postnote.data.repository.NoteRepositoryImpl
import com.adrianotelesc.postnote.ui.screen.noteeditor.NoteEditorViewModel
import com.adrianotelesc.postnote.ui.screen.notes.NotesViewModel
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
        NoteEditorViewModel(
            noteId = params[0],
            noteRepo = get()
        )
    }
}

private val repoModule = module {
    singleOf<NoteRepository>(::NoteRepositoryImpl)
}
